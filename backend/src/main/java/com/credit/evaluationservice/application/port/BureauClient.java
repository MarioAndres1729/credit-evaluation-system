package com.credit.evaluationservice.application.port;

import com.credit.evaluationservice.domain.model.BureauCreditInformation;

public interface BureauClient {

    BureauCreditInformation consultar(
            String tipoDocumento,
            String numeroDocumento
    );
}