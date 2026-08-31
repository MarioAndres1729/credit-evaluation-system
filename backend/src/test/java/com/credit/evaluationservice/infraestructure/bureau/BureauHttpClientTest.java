package com.credit.evaluationservice.infraestructure.bureau;

import com.credit.evaluationservice.domain.exception.BureauUnavailableException;
import com.credit.evaluationservice.domain.model.BureauCreditInformation;
import com.credit.evaluationservice.infrastructure.bureau.BureauHttpClient;
import com.credit.evaluationservice.infrastructure.bureau.dto.BureauResponseDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("rawtypes")
class BureauHttpClientTest {

    private WebClient webClient;

    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    private WebClient.RequestBodySpec requestBodySpec;
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    private WebClient.ResponseSpec responseSpec;

    private BureauHttpClient bureauHttpClient;

    @BeforeEach
    void setUp() {

        webClient = mock(WebClient.class);

        requestBodyUriSpec =
                mock(WebClient.RequestBodyUriSpec.class);

        requestBodySpec =
                mock(WebClient.RequestBodySpec.class);

        requestHeadersSpec =
                mock(WebClient.RequestHeadersSpec.class);

        responseSpec =
                mock(WebClient.ResponseSpec.class);

        bureauHttpClient =
                new BureauHttpClient(webClient);
    }

    @Test
    void debeConsultarBureauCorrectamente() {

        LocalDateTime fechaConsulta =
                LocalDateTime.of(
                        2026, 8, 30, 20, 0
                );

        BureauResponseDTO bureauResponse =
                new BureauResponseDTO(
                        750,
                        "VIGENTE",
                        false,
                        fechaConsulta
                );

        when(webClient.post())
                .thenReturn(requestBodyUriSpec);

        when(requestBodyUriSpec.uri("/api/buro/consulta"))
                .thenReturn(requestBodySpec);

        when(requestBodySpec.bodyValue(any()))
                .thenReturn(requestHeadersSpec);

        when(requestHeadersSpec.retrieve())
                .thenReturn(responseSpec);

        when(responseSpec.bodyToMono(BureauResponseDTO.class))
                .thenReturn(Mono.just(bureauResponse));//

        BureauCreditInformation result =
                bureauHttpClient.consultar(
                        "CC",
                        "1234567890"
                );

        assertNotNull(result);

        assertEquals(
                750,
                result.getScore()
        );

        assertEquals(
                "VIGENTE",
                result.getEstado()
        );

        assertFalse(
                result.isReporteNegativo()
        );

        assertEquals(
                fechaConsulta,
                result.getFechaConsulta()
        );

        verify(webClient).post();

        verify(requestBodyUriSpec)
                .uri("/api/buro/consulta");

        verify(requestBodySpec)
                .bodyValue(any());

        verify(requestHeadersSpec)
                .retrieve();

        verify(responseSpec)
                .bodyToMono(BureauResponseDTO.class);
    }

    @Test
    void debeLanzarBureauUnavailableExceptionCuandoFallaLaConsulta() {

        when(webClient.post())
                .thenReturn(requestBodyUriSpec);

        when(requestBodyUriSpec.uri("/api/buro/consulta"))
                .thenReturn(requestBodySpec);

        when(requestBodySpec.bodyValue(any()))
                .thenReturn(requestHeadersSpec);

        when(requestHeadersSpec.retrieve())
                .thenThrow(
                        new RuntimeException(
                                "Bureau no disponible"
                        )
                );

        assertThrows(
                BureauUnavailableException.class,
                () -> bureauHttpClient.consultar(
                        "CC",
                        "1234567890"
                )
        );
    }
}
