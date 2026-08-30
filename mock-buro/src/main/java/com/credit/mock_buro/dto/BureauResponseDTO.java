package com.credit.mock_buro.dto;

import java.time.LocalDateTime;

public class BureauResponseDTO {

    private int score;
    private String estado;
    private boolean reporteNegativo;
    private LocalDateTime fechaConsulta;

    public BureauResponseDTO(
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
