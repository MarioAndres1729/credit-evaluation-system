package com.credit.evaluationservice.presentation;

import com.credit.evaluationservice.application.decision.EvaluationResult;
import com.credit.evaluationservice.application.service.CreditApplicationService;
import com.credit.evaluationservice.domain.CreditApplication;
import com.credit.evaluationservice.domain.validation.ValidationResult;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/solicitudes")
@CrossOrigin(origins = "*")
public class CreditApplicationController {

    private final CreditApplicationService creditEvaluationService;

    public CreditApplicationController(CreditApplicationService creditEvaluationService) {
        this.creditEvaluationService = creditEvaluationService;
    }

    /**
     * Recibe la solicitud de crédito, la delega al servicio para pasarla por el 
     * DecisionEngine (comenzando con IdentityValidation) y retorna el resultado.
     */
    @PostMapping
    public ResponseEntity<EvaluationResult> crearSolicitud(
            @Valid @RequestBody CreditApplication request) {
        EvaluationResult response = creditEvaluationService.evaluate(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}