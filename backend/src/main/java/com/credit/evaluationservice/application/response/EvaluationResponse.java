package com.credit.evaluationservice.application.response;

import java.time.LocalDateTime;
import java.util.List;

public class EvaluationResponse {

    private String idSolicitud;
    private LocalDateTime fechaCreacion;
    private String estado;

    private ApplicantInfo solicitante;
    private CreditDetail detalle;
    private EvaluationDetail evaluacion;

    private String siguientePaso;

    public EvaluationResponse() {
    }

    public EvaluationResponse(
            String idSolicitud,
            LocalDateTime fechaCreacion,
            String estado,
            ApplicantInfo solicitante,
            CreditDetail detalle,
            EvaluationDetail evaluacion,
            String siguientePaso) {

        this.idSolicitud = idSolicitud;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.solicitante = solicitante;
        this.detalle = detalle;
        this.evaluacion = evaluacion;
        this.siguientePaso = siguientePaso;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public ApplicantInfo getSolicitante() {
        return solicitante;
    }

    public CreditDetail getDetalle() {
        return detalle;
    }

    public EvaluationDetail getEvaluacion() {
        return evaluacion;
    }

    public String getSiguientePaso() {
        return siguientePaso;
    }
}
