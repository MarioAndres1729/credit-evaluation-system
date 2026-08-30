package com.credit.evaluationservice.domain.model;

import java.time.LocalDateTime;

public class BureauCreditInformation {

    private final int score;
    private final String estado;
    private final boolean reporteNegativo;
    private final LocalDateTime fechaConsulta;

    public BureauCreditInformation(
            int score,
            String estado,
            boolean reporteNegativo,
            LocalDateTime fechaConsulta) {
        this.score = score;
        this.estado = estado;
        this.reporteNegativo = reporteNegativo;
        this.fechaConsulta = fechaConsulta;
    }

    public int getScore() {
        return score;
    }

    public String getEstado() {
        return estado;
    }

    public boolean isReporteNegativo() {
        return reporteNegativo;
    }

    public LocalDateTime getFechaConsulta() {
        return fechaConsulta;
    }
}