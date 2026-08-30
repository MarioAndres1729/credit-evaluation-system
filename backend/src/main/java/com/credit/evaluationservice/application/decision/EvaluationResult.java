package com.credit.evaluationservice.application.decision;

import com.credit.evaluationservice.domain.validation.ValidationResult;

import java.util.List;

public class EvaluationResult {

    private final List<ValidationResult> validaciones;

    public EvaluationResult(List<ValidationResult> validaciones) {
        this.validaciones = validaciones;
    }

    public List<ValidationResult> getValidaciones() {
        return validaciones;
    }

    public boolean isAprobado() {
        return validaciones.stream()
                .allMatch(ValidationResult::isAprobado);
    }
}
