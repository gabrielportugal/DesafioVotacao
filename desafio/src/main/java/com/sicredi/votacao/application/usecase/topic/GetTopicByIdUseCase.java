package com.sicredi.votacao.application.usecase.topic;

import com.sicredi.votacao.domain.model.Topic;
import com.sicredi.votacao.domain.repository.TopicRepository;
import com.sicredi.votacao.exceptions.NotFoundException;

import org.springframework.stereotype.Service;

@Service
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
