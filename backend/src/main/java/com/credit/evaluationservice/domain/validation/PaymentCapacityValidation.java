package com.credit.evaluationservice.domain.validation;

import com.credit.evaluationservice.domain.CreditApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class PaymentCapacityValidation implements Validation {

    private static final long MAX_AMOUNT_MULTIPLIER = 10;

    @Override
    public ValidationResult validate(CreditApplication application) {

        long montoSolicitado = application.getMontoSolicitado();
        long ingresosMensuales = application.getIngresosMensuales();

        long montoMaximoPermitido =
                ingresosMensuales * MAX_AMOUNT_MULTIPLIER;

        if (montoSolicitado <= montoMaximoPermitido) {
            return new ValidationResult(
                    "Capacidad de pago",
                    true,
                    "Monto dentro del rango permitido"
            );
        }

        return new ValidationResult(
                "Capacidad de pago",
                false,
                "El monto solicitado excede el límite permitido según los ingresos"
        );
    }
}
