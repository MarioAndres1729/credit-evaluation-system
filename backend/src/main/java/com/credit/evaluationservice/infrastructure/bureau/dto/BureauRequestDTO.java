package com.credit.evaluationservice.infrastructure.bureau.dto;


public class BureauRequestDTO {

    private String tipoDocumento;
    private String numeroDocumento;

    public BureauRequestDTO() {
    }

    public BureauRequestDTO(String tipoDocumento, String numeroDocumento) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }
}