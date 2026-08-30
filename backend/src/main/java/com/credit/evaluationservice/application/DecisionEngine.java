package com.credit.evaluationservice.application;

import com.credit.evaluationservice.domain.CreditApplication;
import com.credit.evaluationservice.domain.validation.Validation;
import com.credit.evaluationservice.domain.validation.ValidationResult;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DecisionEngine {

    private final List<Validation> validations;

    public DecisionEngine(List<Validation> validations) {
        this.validations = validations;
    }

    public ValidationResult evaluate(CreditApplication application) {

        for (Validation validation : validations) {
            ValidationResult result = validation.validate(application);

            if (!result.isApproved()) {
                return result;
            }
        }

        return new ValidationResult("DecisionEngine", true, "All validations passed.");
    }
}