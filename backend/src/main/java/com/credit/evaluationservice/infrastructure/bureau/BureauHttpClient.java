package com.credit.evaluationservice.infrastructure.bureau;

import com.credit.evaluationservice.application.port.BureauClient;
import com.credit.evaluationservice.domain.model.BureauCreditInformation;
import com.credit.evaluationservice.infrastructure.bureau.dto.BureauRequestDTO;
import com.credit.evaluationservice.infrastructure.bureau.dto.BureauResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BureauHttpClient implements BureauClient {

    private final WebClient webClient;

    public BureauHttpClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public BureauCreditInformation consultar(
            String tipoDocumento,
            String numeroDocumento) {

        BureauRequestDTO request =
                new BureauRequestDTO(tipoDocumento, numeroDocumento);

        BureauResponseDTO response = webClient
                .post()
                .uri("/api/buro/consulta")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(BureauResponseDTO.class)
                .block();

        return new BureauCreditInformation(
                response.getScore(),
                response.getEstado(),
                response.isReporteNegativo(),
                response.getFechaConsulta()
        );
    }
}