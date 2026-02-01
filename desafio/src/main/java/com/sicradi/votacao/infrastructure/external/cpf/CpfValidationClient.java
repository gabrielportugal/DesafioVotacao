package com.sicradi.votacao.infrastructure.external.cpf;

import com.sicradi.votacao.infrastructure.external.cpf.CpfValidationResponse;

public interface CpfValidationClient {
    CpfValidationResponse validate();
}