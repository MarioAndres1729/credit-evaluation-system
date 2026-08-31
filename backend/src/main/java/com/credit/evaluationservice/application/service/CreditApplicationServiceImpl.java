package com.credit.evaluationservice.application.service;

import com.credit.evaluationservice.application.decision.DecisionEngine;
import com.credit.evaluationservice.application.response.EvaluationResponse;
import com.credit.evaluationservice.application.response.EvaluationResponseBuilder;
import com.credit.evaluationservice.application.response.SolicitudConsultaResponse;
import com.credit.evaluationservice.domain.CreditApplication;
import com.credit.evaluationservice.domain.exception.BureauUnavailableException;
import com.credit.evaluationservice.domain.validation.ValidationResult;
import com.credit.evaluationservice.infrastructure.persistence.SolicitudesEntity;
import com.credit.evaluationservice.infrastructure.persistence.SolicitudesRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CreditApplicationServiceImpl
        implements CreditApplicationService {

    private final DecisionEngine decisionEngine;
    private final SolicitudesRepository solicitudesRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CreditApplicationServiceImpl(
            DecisionEngine decisionEngine,
            SolicitudesRepository solicitudesRepository) {
        this.decisionEngine = decisionEngine;
        this.solicitudesRepository = solicitudesRepository;
    }

    @Override
    public EvaluationResponse evaluate(
            CreditApplication application) {

            long cantidad = solicitudesRepository
                    .countByTipoDocumentoAndNumeroDocumento(
                            application.getTipoDocumento(),
                            application.getNumeroDocumento()
                    );

            long numSolicitudes = cantidad + 1;
            String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String idSolicitud = String.format("SOL-%s-%03d", datePart, numSolicitudes);  

        try {

            List<ValidationResult> validationResults =
                    decisionEngine.evaluate(application);

            EvaluationResponse response =
                    new EvaluationResponseBuilder(idSolicitud)
                            .construirSolicitante(application)
                            .construirEvaluacion(validationResults)
                            .construirResultado(
                                    application,
                                    validationResults
                            )
                            .build();

            guardarSolicitud(application, response);

            return response;

        } catch (BureauUnavailableException e) {

            EvaluationResponse response =
                    new EvaluationResponseBuilder(idSolicitud)
                            .construirSolicitante(application)
                            .construirBureauUnavailable()
                            .build();

            guardarSolicitud(application, response);

            return response;
        }
    }

    private void guardarSolicitud(
            CreditApplication application,
            EvaluationResponse response) {

        SolicitudesEntity entity = new SolicitudesEntity();

        entity.setIdSolicitud(response.getIdSolicitud());
        entity.setFechaCreacion(response.getFechaCreacion());
        entity.setTipoDocumento(application.getTipoDocumento());
        entity.setNumeroDocumento(application.getNumeroDocumento());
        entity.setNombresApellidos(application.getNombresApellidos());
        entity.setCorreoElectronico(application.getCorreoElectronico());
        entity.setTelefonoCelular(application.getTelefonoCelular());
        entity.setMontoSolicitado(application.getMontoSolicitado());
        entity.setPlazoMeses(application.getPlazoMeses());
        entity.setEstado(response.getEstado());

        if (response.getDetalle() != null) {
            entity.setTasaEstimada(response.getDetalle().getTasaEstimada());
        }

        if (response.getEvaluacion() != null) {
            entity.setScoreBureau(response.getEvaluacion().getScoreBureau());
            try {
                entity.setValidaciones(objectMapper.writeValueAsString(
                    response.getEvaluacion().getValidaciones()));
            } catch (JsonProcessingException e) {
                throw new IllegalStateException(
                    "No fue posible guardar las validaciones", e);
            }
        }

        entity.setSiguientePaso(response.getSiguientePaso());

        solicitudesRepository.save(entity);
    }
        
    @Override
    public List<SolicitudConsultaResponse> consultarSolicitudes(
            String tipoDocumento,
            String numeroDocumento) {

        List<SolicitudesEntity> solicitudes =
                solicitudesRepository
                        .findByTipoDocumentoAndNumeroDocumento(
                                tipoDocumento,
                                numeroDocumento);

        return solicitudes.stream()
                .map(this::convertirARespuesta)
                .toList();
    }

    private SolicitudConsultaResponse convertirARespuesta(
            SolicitudesEntity entity) {

        List<ValidationResult> validaciones = null;

        if (entity.getValidaciones() != null) {
            try {
                validaciones = objectMapper.readValue(
                        entity.getValidaciones(),
                        objectMapper.getTypeFactory()
                                .constructCollectionType(
                                        List.class,
                                        ValidationResult.class
                                ));
            } catch (JsonProcessingException e) {
                throw new IllegalStateException(
                        "No fue posible leer las validaciones",
                        e
                );
            }
        }

        return new SolicitudConsultaResponse(
                entity.getIdSolicitud(),
                entity.getMontoSolicitado(),
                entity.getPlazoMeses(),
                entity.getTasaEstimada(),
                entity.getScoreBureau(),
                entity.getEstado(),
                validaciones,
                entity.getSiguientePaso()
        );
    }
}
