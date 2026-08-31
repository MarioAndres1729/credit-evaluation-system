package com.credit.evaluationservice.application.decision;

import com.credit.evaluationservice.domain.CreditApplication;
import com.credit.evaluationservice.domain.validation.Validation;
import com.credit.evaluationservice.domain.validation.ValidationResult;
import com.credit.evaluationservice.domain.validation.ValidationStatus;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DecisionEngine {

    private final List<Validation> validations;

    public DecisionEngine(List<Validation> validations) {
        this.validations = validations;
    }

    public List<ValidationResult> evaluate(
            CreditApplication application) {

        List<ValidationResult> results = new ArrayList<>();

        for (Validation validation : validations) {

            ValidationResult result =
                    validation.validate(application);

            results.add(result);

            /*
             * Se detiene la evaluación si esta validación
             * no ha aprobado la solicitud.
             */
            if (result.getResultado() != ValidationStatus.APROBADO) {
                break;
            }
        }

        return results;
    }
}