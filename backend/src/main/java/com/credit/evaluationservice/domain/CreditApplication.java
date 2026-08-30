package com.credit.evaluationservice.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class CreditApplication {

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Pattern(
            regexp = "CC|CE|PA",
            message = "El tipo de documento debe ser CC, CE o PA"
    )
    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    @Pattern(
            regexp = "^[0-9]{6,12}$",
            message = "El número de documento debe ser numérico y contener entre 6 y 12 dígitos"
    )
    private String numeroDocumento;

    @NotBlank(message = "Los nombres y apellidos son obligatorios")
    private String nombresApellidos;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no tiene un formato válido")
    private String correoElectronico;

    @NotBlank(message = "El teléfono celular es obligatorio")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "El teléfono celular debe contener 10 dígitos"
    )
    private String telefonoCelular;

    @NotNull(message = "El monto solicitado es obligatorio")
    @Min(value = 1000000, message = "El monto solicitado debe ser mínimo de $1.000.000")
    @Max(value = 50000000, message = "El monto solicitado debe ser máximo de $50.000.000")
    private Long montoSolicitado;

    @NotNull(message = "El plazo en meses es obligatorio")
    private Integer plazoMeses;

    @NotNull(message = "Los ingresos mensuales son obligatorios")
    @Positive(message = "Los ingresos mensuales deben ser mayores a cero")
    private Long ingresosMensuales;

    public CreditApplication() {
    }

    public CreditApplication(
            String tipoDocumento,
            String numeroDocumento,
            String nombresApellidos,
            String correoElectronico,
            String telefonoCelular,
            Long montoSolicitado,
            Integer plazoMeses,
            Long ingresosMensuales) {

        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombresApellidos = nombresApellidos;
        this.correoElectronico = correoElectronico;
        this.telefonoCelular = telefonoCelular;
        this.montoSolicitado = montoSolicitado;
        this.plazoMeses = plazoMeses;
        this.ingresosMensuales = ingresosMensuales;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombresApellidos() {
        return nombresApellidos;
    }

    public void setNombresApellidos(String nombresApellidos) {
        this.nombresApellidos = nombresApellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public Long getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(Long montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public Integer getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(Integer plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public Long getIngresosMensuales() {
        return ingresosMensuales;
    }

    public void setIngresosMensuales(Long ingresosMensuales) {
        this.ingresosMensuales = ingresosMensuales;
    }
}