package com.sicredi.votacao;

import com.sicredi.votacao.application.usecase.ValidateCpfUseCase;
import com.sicredi.votacao.exceptions.InvalidCpfException;
import com.sicredi.votacao.exceptions.UnableToVoteException;
import com.sicredi.votacao.infrastructure.external.cpf.CpfValidationClient;
import com.sicredi.votacao.infrastructure.external.cpf.CpfValidationResponse;
import com.sicredi.votacao.infrastructure.external.cpf.CpfValidationStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ValidateCpfUseCaseTest {
    @Test
    void cpfInvalidoDeveLancarException() {
        CpfValidationClient client = Mockito.mock(CpfValidationClient.class);
        Mockito.when(client.validate()).thenThrow(new InvalidCpfException("CPF inválido"));
        ValidateCpfUseCase useCase = new ValidateCpfUseCase(client);
        assertThrows(InvalidCpfException.class, useCase::execute);
    }

    @Test
    void cpfValidoAbleToVoteRetornaSucesso() {
        CpfValidationClient client = Mockito.mock(CpfValidationClient.class);
        CpfValidationResponse resp = new CpfValidationResponse("12345678901", CpfValidationStatus.ABLE_TO_VOTE);
        Mockito.when(client.validate()).thenReturn(resp);
        ValidateCpfUseCase useCase = new ValidateCpfUseCase(client);
        CpfValidationResponse result = useCase.execute();
        assertEquals("12345678901", result.getCpf());
        assertEquals(CpfValidationStatus.ABLE_TO_VOTE, result.getStatus());
    }

    @Test
    void cpfValidoUnableToVoteRetornaErro() {
        CpfValidationClient client = Mockito.mock(CpfValidationClient.class);
        Mockito.when(client.validate()).thenThrow(new UnableToVoteException("Não pode votar"));
        ValidateCpfUseCase useCase = new ValidateCpfUseCase(client);
        assertThrows(UnableToVoteException.class, useCase::execute);
    }
}