package com.sicradi.votacao.exceptions;

public class VotingSessionNotFoundException extends RuntimeException {
    public VotingSessionNotFoundException(Long id) {
        super("Sessão de votação não encontrada para o ID informado: " + id);
    }
}
