package com.sicredi.votacao.infrastructure.external.cpf;

public class CpfValidationResponse {
    private String cpf;
    private CpfValidationStatus status;

    public CpfValidationResponse(String cpf, CpfValidationStatus status) {
        this.cpf = cpf;
        this.status = status;
    }

    public String getCpf() {
        return cpf;
    }

    public CpfValidationStatus getStatus() {
        return status;
    }
}