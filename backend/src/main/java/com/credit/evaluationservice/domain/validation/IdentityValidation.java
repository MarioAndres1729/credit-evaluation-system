package com.credit.evaluationservice.domain.validation;

import java.util.Set;
import org.springframework.stereotype.Component;
import com.credit.evaluationservice.domain.CreditApplication;

@Component
public class IdentityValidation implements Validation {

    private final Set<String> blockedDocuments = Set.of(
        "1111111111",
        "2222222222",
        "3333333333"
    );

    @Override
    public ValidationResult validate(CreditApplication application) {

        boolean blocked = blockedDocuments.contains(
            application.getDocumentNumber()
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
