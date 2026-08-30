package com.credit.evaluationservice.domain.validation;


public class ValidationResult {

    private final String nombreValidacion;
    private final boolean resultado;
    private final String detalle;

    public ValidationResult(
            String nombreValidacion,
            boolean resultado,
            String detalle) {
        this.nombreValidacion = nombreValidacion;
        this.resultado = resultado;
        this.detalle = detalle;
    }

    public String getNombreValidacion() {
        return nombreValidacion;
    }

    public boolean isAprobado() {
        return resultado;
    }

    public String getDetalle() {
        return detalle;
    }
}