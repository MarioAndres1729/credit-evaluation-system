package com.credit.evaluationservice.domain.validation;

import com.credit.evaluationservice.application.port.BureauClient;
import com.credit.evaluationservice.domain.CreditApplication;
import com.credit.evaluationservice.domain.model.BureauCreditInformation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class NegativeReportValidation implements Validation {

    private final BureauClient bureauClient;

    public NegativeReportValidation(BureauClient bureauClient) {
        this.bureauClient = bureauClient;
    }

    @Override
    public ValidationResult validate(CreditApplication application) {

        BureauCreditInformation bureauInformation =
                bureauClient.consultar(
                        application.getTipoDocumento(),
                        application.getNumeroDocumento()
                );

        if (bureauInformation.isReporteNegativo()) {
            return new ValidationResult(
                    "Reporte negativo",
                    false,
                    "El cliente presenta reporte negativo en el buró"
            );
        }

        return new ValidationResult(
                "Reporte negativo",
                true,
                "Sin reportes negativos"
        );
    }
}