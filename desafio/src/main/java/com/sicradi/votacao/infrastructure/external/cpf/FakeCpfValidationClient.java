package com.sicradi.votacao.infrastructure.external.cpf;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

import com.sicradi.votacao.exceptions.InvalidCpfException;

@Component
public class FakeCpfValidationClient implements CpfValidationClient {
    private final SecureRandom random = new SecureRandom();

    @Override
    public CpfValidationResponse validate() {
        int scenario = random.nextInt(3); // 0: inválido, 1: válido/ABLE, 2: válido/UNABLE
        String cpf = CpfGenerator.generate(random);
        if (scenario == 0) {
            throw new InvalidCpfException("CPF inválido gerado: " + cpf);
        } else if (scenario == 1) {
            return new CpfValidationResponse(cpf, CpfValidationStatus.ABLE_TO_VOTE);
        } else {
            return new CpfValidationResponse(cpf, CpfValidationStatus.UNABLE_TO_VOTE);
        }
    }
}