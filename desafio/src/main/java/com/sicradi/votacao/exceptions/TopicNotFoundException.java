package com.sicradi.votacao.exceptions;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(Long topicId) {
        super("Tópico não encontrado para o ID informado: " + topicId);
    }
}
