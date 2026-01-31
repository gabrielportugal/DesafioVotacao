package com.sicradi.votacao.application.usecase.topic;

import com.sicradi.votacao.domain.model.Topic;
import com.sicradi.votacao.domain.repository.TopicRepository;
import com.sicradi.votacao.exceptions.ResourceNotFoundException;

public class GetTopicByIdUseCase {
    private final TopicRepository topicRepository;

    public GetTopicByIdUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic execute(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id " + id));
    }
}
