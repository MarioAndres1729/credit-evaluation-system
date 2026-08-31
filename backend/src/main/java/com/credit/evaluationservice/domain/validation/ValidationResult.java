package com.credit.evaluationservice.domain.validation;

public class ValidationResult {

    private final String nombre;
    private final ValidationStatus resultado;
    private final String detalle;
    private final Integer scoreBureau;

    public ValidationResult(
            String nombre,
            ValidationStatus resultado,
            String detalle) {

        this(nombre, resultado, detalle, null);
    }

    public ValidationResult(
            String nombre,
            ValidationStatus resultado,
            String detalle,
            Integer scoreBureau) {

        this.nombre = nombre;
        this.resultado = resultado;
        this.detalle = detalle;
        this.scoreBureau = scoreBureau;
    }

    public String getNombre() {
        return nombre;
    }

    public ValidationStatus getResultado() {
        return resultado;
    }

    public String getDetalle() {
        return detalle;
    }

    public Integer getScoreBureau() {
        return scoreBureau;
    }

    public boolean isAprobado() {
        return resultado == ValidationStatus.APROBADO;
    }
}
