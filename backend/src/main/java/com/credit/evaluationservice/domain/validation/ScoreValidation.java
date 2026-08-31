package com.credit.evaluationservice.domain.validation;

import com.credit.evaluationservice.application.port.BureauClient;
import com.credit.evaluationservice.domain.CreditApplication;
import com.credit.evaluationservice.domain.model.BureauCreditInformation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class ScoreValidation implements Validation {

    private static final int MINIMUM_SCORE = 600;

    private final BureauClient bureauClient;

    public ScoreValidation(BureauClient bureauClient) {
        this.bureauClient = bureauClient;
    }

    @Override
    public ValidationResult validate(
            CreditApplication application) {

        BureauCreditInformation bureauInformation =
                bureauClient.consultar(
                        application.getTipoDocumento(),
                        application.getNumeroDocumento()
                );

        int score = bureauInformation.getScore();

        if (score >= MINIMUM_SCORE) {

            return new ValidationResult(
                    "Score",
                    ValidationStatus.APROBADO,
                    "Score " + score + " >= " + MINIMUM_SCORE,
                    score
            );
        }

        return new ValidationResult(
                "Score",
                ValidationStatus.RECHAZADO,
                "Score " + score + " < " + MINIMUM_SCORE,
                score
        );
    }
}