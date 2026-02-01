package com.sicredi.votacao.application.usecase;

import com.sicredi.votacao.infrastructure.external.cpf.CpfValidationClient;
import com.sicredi.votacao.infrastructure.external.cpf.CpfValidationResponse;
import org.springframework.stereotype.Service;

@Service
public class ValidateCpfUseCase {
    private final CpfValidationClient cpfValidationClient;

    public ValidateCpfUseCase(CpfValidationClient cpfValidationClient) {
        this.cpfValidationClient = cpfValidationClient;
    }

    public CpfValidationResponse execute() {
        return cpfValidationClient.validate();
    }
}