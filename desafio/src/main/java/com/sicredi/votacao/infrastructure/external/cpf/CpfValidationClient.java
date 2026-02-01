package com.sicredi.votacao.infrastructure.external.cpf;

import com.sicredi.votacao.infrastructure.external.cpf.CpfValidationResponse;

public interface CpfValidationClient {
    CpfValidationResponse validate();
}