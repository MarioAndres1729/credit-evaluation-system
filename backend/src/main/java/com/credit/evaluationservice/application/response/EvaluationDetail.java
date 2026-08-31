package com.credit.evaluationservice.application.response;

import com.credit.evaluationservice.domain.validation.ValidationResult;

import java.util.List;

public class EvaluationDetail {

    private final Integer scoreBureau;
    private final List<ValidationResult> validaciones;

    public EvaluationDetail(
            Integer scoreBureau,
            List<ValidationResult> validaciones) {

        this.scoreBureau = scoreBureau;
        this.validaciones = validaciones;
    }

    public Integer getScoreBureau() {
        return scoreBureau;
    }

    public List<ValidationResult> getValidaciones() {
        return validaciones;
    }
}