package com.sicredi.votacao.infrastructure.external.cpf;

import java.security.SecureRandom;

public class CpfGenerator {
    public static String generate(SecureRandom random) {
        StringBuilder cpf = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            cpf.append(random.nextInt(10));
        }
        return cpf.toString();
    }
}