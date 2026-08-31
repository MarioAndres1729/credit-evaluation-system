package com.credit.evaluationservice.infrastructure.persistence;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "solicitudes")
public class SolicitudesEntity {

    @Id
    private String idSolicitud;

    private LocalDateTime fechaCreacion;

    private String tipoDocumento;

    private String numeroDocumento;

    private String nombresApellidos;

    private String correoElectronico;

    private String telefonoCelular;

    private Long montoSolicitado;

    private Integer plazoMeses;

    private Double tasaEstimada;

    private Integer scoreBureau;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String validaciones;

    private String estado;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String siguientePaso;

    public SolicitudesEntity() {
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public Double getTasaEstimada() {
        return tasaEstimada;
    }

    public void setTasaEstimada(Double tasaEstimada) {
        this.tasaEstimada = tasaEstimada;
    }

    public Integer getScoreBureau() {
        return scoreBureau;
    }

    public void setScoreBureau(Integer scoreBureau) {
        this.scoreBureau = scoreBureau;
    }

    public String getValidaciones() {
        return validaciones;
    }

    public void setValidaciones(String validaciones) {
        this.validaciones = validaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSiguientePaso() {
        return siguientePaso;
    }

    public void setSiguientePaso(String siguientePaso) {
        this.siguientePaso = siguientePaso;
    }
}

