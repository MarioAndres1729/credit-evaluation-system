package com.credit.evaluationservice.application.response;

public class CreditDetail {

    private final Long montoSolicitado;
    private final Integer plazoMeses;
    private final Double tasaEstimada;

    public CreditDetail(
            Long montoSolicitado,
            Integer plazoMeses,
            Double tasaEstimada) {

        this.montoSolicitado = montoSolicitado;
        this.plazoMeses = plazoMeses;
        this.tasaEstimada = tasaEstimada;
    }

    public Long getMontoSolicitado() {
        return montoSolicitado;
    }

    public Integer getPlazoMeses() {
        return plazoMeses;
    }

    public Double getTasaEstimada() {
        return tasaEstimada;
    }
}