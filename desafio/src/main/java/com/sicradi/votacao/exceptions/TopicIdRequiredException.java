package com.sicradi.votacao.exceptions;

public class TopicIdRequiredException extends RuntimeException {
    public TopicIdRequiredException() {
        super("O ID do tópico é obrigatório para abrir uma sessão de votação.");
    }
}
