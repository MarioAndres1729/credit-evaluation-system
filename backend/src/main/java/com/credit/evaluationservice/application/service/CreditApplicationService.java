package com.credit.evaluationservice.application.service;

import com.credit.evaluationservice.application.decision.EvaluationResult;
import com.credit.evaluationservice.domain.CreditApplication;

public interface CreditApplicationService {
    EvaluationResult evaluate(CreditApplication request);
}
