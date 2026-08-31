package com.credit.evaluationservice.application.response;

import com.credit.evaluationservice.domain.validation.ValidationResult;
import java.util.List;

public class SolicitudConsultaResponse {

    private String idSolicitud;
    private long montoSolicitado;
    private int plazoMeses;
    private Double tasaEstimada;
    private Integer scoreBureau;
    private String estado;
    private List<ValidationResult> validaciones;
    private String siguientePaso;

    public SolicitudConsultaResponse() {
    }

    public SolicitudConsultaResponse(
            String idSolicitud,
            long montoSolicitado,
            int plazoMeses,
            Double tasaEstimada,
            Integer scoreBureau,
            String estado,
            List<ValidationResult> validaciones,
            String siguientePaso) {

        this.idSolicitud = idSolicitud;
        this.montoSolicitado = montoSolicitado;
        this.plazoMeses = plazoMeses;
        this.tasaEstimada = tasaEstimada;
        this.scoreBureau = scoreBureau;
        this.estado = estado;
        this.validaciones = validaciones;
        this.siguientePaso = siguientePaso;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public long getMontoSolicitado() {
        return montoSolicitado;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public Double getTasaEstimada() {
        return tasaEstimada;
    }

    public Integer getScoreBureau() {
        return scoreBureau;
    }

    public String getEstado() {
        return estado;
    }

    public List<ValidationResult> getValidaciones() {
        return validaciones;
    }

    public String getSiguientePaso() {
        return siguientePaso;
    }
}
