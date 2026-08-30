package com.credit.mock_buro.service;

import com.credit.mock_buro.dto.BureauRequestDTO;
import com.credit.mock_buro.dto.BureauResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BureauMockService {

    public BureauResponseDTO consultar(BureauRequestDTO request) {

        String numeroDocumento = request.getNumeroDocumento();

        if ("0000000000".equals(numeroDocumento)) {
            simularTimeout();
        }

        int ultimoDigito = Character.getNumericValue(
                numeroDocumento.charAt(numeroDocumento.length() - 1)
        );

        if (ultimoDigito % 2 == 0) {
            return new BureauResponseDTO(
                    750,
                    "ACTIVO",
                    false,
                    LocalDateTime.now()
            );
        }

        return new BureauResponseDTO(
                450,
                "ACTIVO",
                true,
                LocalDateTime.now()
        );
    }

    private void simularTimeout() {
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
