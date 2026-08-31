package com.credit.evaluationservice.application.service;

import java.util.List;

import com.credit.evaluationservice.application.response.EvaluationResponse;
import com.credit.evaluationservice.application.response.SolicitudConsultaResponse;
import com.credit.evaluationservice.domain.CreditApplication;

public interface CreditApplicationService {
    EvaluationResponse evaluate(CreditApplication request);
    List<SolicitudConsultaResponse> consultarSolicitudes(String tipoDocumento,
                                                    String numeroDocumento);
}
