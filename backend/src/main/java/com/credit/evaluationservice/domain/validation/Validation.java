package com.credit.evaluationservice.domain.validation;

import com.credit.evaluationservice.domain.CreditApplication;

public interface Validation {

    ValidationResult validate(CreditApplication application);
}
