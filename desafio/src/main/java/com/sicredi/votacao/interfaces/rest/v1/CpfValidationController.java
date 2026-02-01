package com.sicredi.votacao.interfaces.rest.v1;

import com.sicredi.votacao.application.usecase.ValidateCpfUseCase;
import com.sicredi.votacao.infrastructure.external.cpf.CpfValidationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cpf/validation")
public class CpfValidationController {
    private final ValidateCpfUseCase validateCpfUseCase;

    public CpfValidationController(ValidateCpfUseCase validateCpfUseCase) {
        this.validateCpfUseCase = validateCpfUseCase;
    }

    @PostMapping
    public ResponseEntity<CpfValidationResponse> validate() {
        CpfValidationResponse response = validateCpfUseCase.execute();
        return ResponseEntity.ok(response);
    }
}