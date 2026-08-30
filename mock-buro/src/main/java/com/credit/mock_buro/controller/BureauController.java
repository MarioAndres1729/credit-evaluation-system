package com.credit.mock_buro.controller;

import com.credit.mock_buro.dto.BureauRequestDTO;
import com.credit.mock_buro.dto.BureauResponseDTO;
import com.credit.mock_buro.service.BureauMockService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buro")
public class BureauController {

    private final BureauMockService bureauMockService;

    public BureauController(BureauMockService bureauMockService) {
        this.bureauMockService = bureauMockService;
    }

    @PostMapping("/consulta")
    public BureauResponseDTO consultar(
            @RequestBody BureauRequestDTO request) {

        return bureauMockService.consultar(request);
    }
}
