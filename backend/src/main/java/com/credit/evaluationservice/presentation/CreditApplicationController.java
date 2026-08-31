package com.credit.evaluationservice.presentation;

import com.credit.evaluationservice.application.response.EvaluationResponse;
import com.credit.evaluationservice.application.response.SolicitudConsultaResponse;
import com.credit.evaluationservice.application.service.CreditApplicationService;
import com.credit.evaluationservice.domain.CreditApplication;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/solicitudes")
@CrossOrigin(origins = "*")
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;

    public CreditApplicationController(
            CreditApplicationService creditApplicationService) {

        this.creditApplicationService = creditApplicationService;
    }

    @PostMapping
    public ResponseEntity<EvaluationResponse> crearSolicitud(
            @Valid @RequestBody CreditApplication request) {

        EvaluationResponse response =
                creditApplicationService.evaluate(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }


    @GetMapping("/{tipoDocumento}/{numeroDocumento}")
    public ResponseEntity<List<SolicitudConsultaResponse>> consultarSolicitudes(
            @PathVariable String tipoDocumento,
            @PathVariable String numeroDocumento) {

        List<SolicitudConsultaResponse> solicitudes =
                creditApplicationService.consultarSolicitudes(
                        tipoDocumento,
                        numeroDocumento
                );

        return ResponseEntity.ok(solicitudes);
    }

}