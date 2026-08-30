package com.credit.evaluationservice.application.service;

import com.credit.evaluationservice.domain.CreditApplication;
import com.credit.evaluationservice.domain.validation.ValidationResult;

public interface CreditApplicationService {
    ValidationResult evaluate(CreditApplication request);
}
