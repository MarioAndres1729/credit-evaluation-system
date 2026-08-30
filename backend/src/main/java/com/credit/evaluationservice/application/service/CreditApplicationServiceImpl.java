package com.credit.evaluationservice.application.service;

import com.credit.evaluationservice.application.decision.DecisionEngine;
import com.credit.evaluationservice.application.decision.EvaluationResult;
import com.credit.evaluationservice.domain.CreditApplication;
import com.credit.evaluationservice.domain.validation.ValidationResult;

import org.springframework.stereotype.Service;

@Service
public class CreditApplicationServiceImpl implements CreditApplicationService {

    private final DecisionEngine decisionEngine;

    public CreditApplicationServiceImpl(DecisionEngine decisionEngine) {
        this.decisionEngine = decisionEngine;
    }

    @Override
    public EvaluationResult evaluate(CreditApplication application) {
        return decisionEngine.evaluate(application);
    }
}
