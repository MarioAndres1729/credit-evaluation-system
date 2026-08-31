package com.credit.evaluationservice.infrastructure.bureau;

import com.credit.evaluationservice.application.port.BureauClient;
import com.credit.evaluationservice.domain.model.BureauCreditInformation;
import com.credit.evaluationservice.infrastructure.bureau.dto.BureauRequestDTO;
import com.credit.evaluationservice.infrastructure.bureau.dto.BureauResponseDTO;

import java.time.Duration;
import java.util.concurrent.TimeoutException;
import com.credit.evaluationservice.domain.exception.BureauUnavailableException;

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
                new BureauRequestDTO(
                        tipoDocumento,
                        numeroDocumento
                );

        try {
                BureauResponseDTO response =
                        webClient.post()
                                .uri("/api/buro/consulta")
                                .bodyValue(request)
                                .retrieve()
                                .bodyToMono(BureauResponseDTO.class)
                                .timeout(Duration.ofSeconds(4))
                                .block();

                return new BureauCreditInformation(
                        response.getScore(),
                        response.getEstado(),
                        response.isReporteNegativo(),
                        response.getFechaConsulta()
                );

        } catch (Exception e) {

            e.printStackTrace();    

            throw new BureauUnavailableException(
                    "No fue posible consultar el servicio de buró",
                    e
            );
        }
    }
}