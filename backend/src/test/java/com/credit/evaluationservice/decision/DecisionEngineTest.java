package com.credit.evaluationservice.decision;

import com.credit.evaluationservice.application.decision.DecisionEngine;
import com.credit.evaluationservice.domain.CreditApplication;
import com.credit.evaluationservice.domain.validation.Validation;
import com.credit.evaluationservice.domain.validation.ValidationResult;
import com.credit.evaluationservice.domain.validation.ValidationStatus;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DecisionEngineTest {

    @Test
    void debeEjecutarValidacionesEnOrden() {

        Validation identidad = mock(Validation.class);
        Validation score = mock(Validation.class);
        Validation capacidad = mock(Validation.class);

        CreditApplication application = new CreditApplication();

        when(identidad.validate(application))
                .thenReturn(new ValidationResult(
                        "Identidad",
                        ValidationStatus.APROBADO,
                        "Documento no bloqueado"
                ));

        when(score.validate(application))
                .thenReturn(new ValidationResult(
                        "Score",
                        ValidationStatus.APROBADO,
                        "Score 750 >= 600"
                ));

        when(capacidad.validate(application))
                .thenReturn(new ValidationResult(
                        "Capacidad de pago",
                        ValidationStatus.APROBADO,
                        "Monto dentro del rango permitido"
                ));

        DecisionEngine engine =
                new DecisionEngine(
                        List.of(identidad, score, capacidad)
                );

        List<ValidationResult> results =
                engine.evaluate(application);

        assertEquals(3, results.size());

        verify(identidad).validate(application);
        verify(score).validate(application);
        verify(capacidad).validate(application);
    }

    @Test
    void debeDetenerseCuandoUnaValidacionFalla() {

        Validation identidad = mock(Validation.class);
        Validation score = mock(Validation.class);
        Validation capacidad = mock(Validation.class);

        CreditApplication application = new CreditApplication();

        when(identidad.validate(application))
                .thenReturn(new ValidationResult(
                        "Identidad",
                        ValidationStatus.APROBADO,
                        "Documento no bloqueado"
                ));

        when(score.validate(application))
                .thenReturn(new ValidationResult(
                        "Score",
                        ValidationStatus.RECHAZADO,
                        "Score 500 < 600"
                ));

        DecisionEngine engine =
                new DecisionEngine(
                        List.of(identidad, score, capacidad)
                );

        List<ValidationResult> results =
                engine.evaluate(application);

        assertEquals(2, results.size());

        verify(identidad).validate(application);
        verify(score).validate(application);

        // La validación posterior NO debe ejecutarse
        verify(capacidad, never()).validate(application);
    }
}