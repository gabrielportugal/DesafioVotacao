package com.sicradi.votacao.application.usecase.topic;

import com.sicradi.votacao.domain.model.Topic;
import com.sicradi.votacao.domain.repository.TopicRepository;
import com.sicradi.votacao.exceptions.NotFoundException;

public class GetTopicByIdUseCase {
    private final TopicRepository topicRepository;

    public GetTopicByIdUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic execute(Long id) {
        return topicRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Tópico não encontrado para o ID informado: " + id));
    }
}
