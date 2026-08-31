package com.credit.evaluationservice.domain.validation;

public class ValidationResult {

    private String nombre;
    private ValidationStatus resultado;
    private String detalle;
    private Integer scoreBureau;

    public ValidationResult() {
    }

    public ValidationResult(
            String nombre,
            ValidationStatus resultado,
            String detalle) {

        this.nombre = nombre;
        this.resultado = resultado;
        this.detalle = detalle;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ValidationStatus getResultado() {
        return resultado;
    }

    public void setResultado(ValidationStatus resultado) {
        this.resultado = resultado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getScoreBureau() {
        return scoreBureau;
    }

    public void setScoreBureau(Integer scoreBureau) {
        this.scoreBureau = scoreBureau;
    }
}
