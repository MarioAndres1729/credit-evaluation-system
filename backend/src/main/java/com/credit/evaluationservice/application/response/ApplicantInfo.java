package com.credit.evaluationservice.application.response;

public class ApplicantInfo {

    private final String nombre;
    private final String documento;

    public ApplicantInfo(String nombre, String documento) {
        this.nombre = nombre;
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }
}