package com.credit.evaluationservice.application.service;

import com.credit.evaluationservice.application.decision.DecisionEngine;
import com.credit.evaluationservice.application.response.EvaluationResponse;
import com.credit.evaluationservice.application.response.EvaluationResponseBuilder;
import com.credit.evaluationservice.domain.CreditApplication;
import com.credit.evaluationservice.domain.exception.BureauUnavailableException;
import com.credit.evaluationservice.domain.validation.ValidationResult;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CreditApplicationServiceImpl
        implements CreditApplicationService {

    private final DecisionEngine decisionEngine;

    public CreditApplicationServiceImpl(
            DecisionEngine decisionEngine) {

        this.decisionEngine = decisionEngine;
    }

    @Override
    public EvaluationResponse evaluate(
            CreditApplication application) {

        try {

            List<ValidationResult> validationResults =
                    decisionEngine.evaluate(application);

            return new EvaluationResponseBuilder()
                    .construirSolicitante(application)
                    .construirEvaluacion(validationResults)
                    .construirResultado(application, validationResults)
                    .build();

        } catch (BureauUnavailableException e) {
            return new EvaluationResponseBuilder()
                    .construirSolicitante(application)
                    .construirBureauUnavailable()
                    .build();
        }
    }
}