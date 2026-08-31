package com.credit.evaluationservice.application.response;

import com.credit.evaluationservice.domain.CreditApplication;
import com.credit.evaluationservice.domain.validation.ValidationResult;
import com.credit.evaluationservice.domain.validation.ValidationStatus;

import java.time.LocalDateTime;
import java.util.List;

public class EvaluationResponseBuilder {

    private String idSolicitud;
    private LocalDateTime fechaCreacion;
    private String estado;

    private ApplicantInfo solicitante;
    private CreditDetail detalle;
    private EvaluationDetail evaluacion;
    private String siguientePaso;

    public EvaluationResponseBuilder(String idSolicitud) {        
        this.idSolicitud = idSolicitud;
        this.fechaCreacion = LocalDateTime.now();
    }


    private Integer obtenerScoreBureau(
            List<ValidationResult> validationResults) {

        return validationResults.stream()
                .filter(result -> "Score".equals(result.getNombre()))
                .map(ValidationResult::getScoreBureau)
                .findFirst()
                .orElse(null);
    }

    /**
     * Construye la información básica del solicitante.
     */
    public EvaluationResponseBuilder construirSolicitante(
            CreditApplication application) {

        this.solicitante = new ApplicantInfo(
                application.getNombresApellidos(),
                application.getTipoDocumento()
                        + " "
                        + application.getNumeroDocumento()
        );

        return this;
    }

    /**
     * Construye la sección de evaluación.
     */
    public EvaluationResponseBuilder construirEvaluacion(
            List<ValidationResult> validationResults) {

        Integer scoreBureau =
                obtenerScoreBureau(validationResults);

        this.evaluacion = new EvaluationDetail(
                scoreBureau,
                validationResults
        );

        return this;
    }

    /**
     * Determina el resultado final de la solicitud.
     *
     * Reglas:
     *
     * score >= 700 && monto <= ingresos * 8
     *      -> APROBADO
     *
     * score >= 600 && monto <= ingresos * 5
     *      -> PREAPROBADO
     *
     * score < 600
     *      -> RECHAZADO
     *
     * reporte negativo
     *      -> RECHAZADO
     *
     * documento bloqueado
     *      -> RECHAZADO_FRAUDE
     */
    public EvaluationResponseBuilder construirResultado(
            CreditApplication application,
            List<ValidationResult> validationResults) {

            Integer scoreBureau =
            obtenerScoreBureau(validationResults);

        /*
         * 1. Documento bloqueado
         */
        boolean documentoBloqueado = validationResults.stream()
                .anyMatch(result ->
                        result.getResultado()
                                == ValidationStatus.DOCUMENTO_BLOQUEADO);

        if (documentoBloqueado) {
            this.estado = "RECHAZADO_FRAUDE";
            return this;
        }

        /*
         * Si no tenemos score, no podemos aplicar
         * las reglas de decisión crediticia.
         */
        if (scoreBureau == null) {
            this.estado = "RECHAZADO"; //revisar esta lógica, si no hay score
            return this;
        }

        /*
         * 2. Score menor a 600
         */
        if (scoreBureau < 600) {
            this.estado = "RECHAZADO";
            return this;
        }

        /*
         * 3. Alguna validación fue rechazada
         */
        boolean validationRejected = validationResults.stream()
                .anyMatch(result ->
                        result.getResultado()
                                == ValidationStatus.RECHAZADO);

        if (validationRejected) {
            this.estado = "RECHAZADO";
            return this;
        }

        long montoSolicitado =
                application.getMontoSolicitado();

        long ingresosMensuales =
                application.getIngresosMensuales();

        /*
         * 4. APROBADO
         *
         * Score >= 700
         * monto <= ingresos * 8
         */
        if (scoreBureau >= 700
                && montoSolicitado <= ingresosMensuales * 8) {
            this.estado = "APROBADO";
            this.detalle = new CreditDetail(
                    montoSolicitado,
                    application.getPlazoMeses(),
                    1.2
            );

            this.siguientePaso =
                    "Se enviará contrato al correo registrado en 24 horas";

            return this;
        }

        /*
         * 5. PREAPROBADO
         *
         * Score >= 600
         * monto <= ingresos * 5
         */
        if (scoreBureau >= 600
                && montoSolicitado <= ingresosMensuales * 5) {

            this.estado = "PREAPROBADO";

            this.detalle = new CreditDetail(
                    montoSolicitado,
                    application.getPlazoMeses(),
                    1.5
            );

            this.siguientePaso =
                    "Se requiere documentación adicional";

            return this;
        }

        /*
         * 6. No cumple las condiciones de aprobación
         */
        this.estado = "RECHAZADO";

        return this;
    }

    /**
     * Construye la respuesta cuando el Bureau no está disponible.
     */
    public EvaluationResponseBuilder construirBureauUnavailable() {

        this.estado = "PENDIENTE_REVISION";

        this.siguientePaso =
                "La solicitud será revisada cuando el servicio de buró esté disponible";

        return this;
    }

    /**
     * Construye la respuesta final.
     */
    public EvaluationResponse build() {

        return new EvaluationResponse(
                idSolicitud,
                fechaCreacion,
                estado,
                solicitante,
                detalle,
                evaluacion,
                siguientePaso
        );
    }
}