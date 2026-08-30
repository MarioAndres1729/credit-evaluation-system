package com.credit.evaluationservice.domain.validation;

import com.credit.evaluationservice.domain.CreditApplication;
import com.credit.evaluationservice.domain.repository.BlacklistRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class IdentityValidation implements Validation {

    private final BlacklistRepository blacklistRepository;

    public IdentityValidation(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    @Override
    public ValidationResult validate(CreditApplication application) {

        boolean blocked = blacklistRepository.isBlocked(
                application.getNumeroDocumento()
        );

        if (blocked) {
            return new ValidationResult(
                    "Identidad",
                    false,
                    "Documento encontrado en lista de bloqueo"
            );
        }

        return new ValidationResult(
                "Identidad",
                true,
                "Documento no bloqueado"
        );
    }
}