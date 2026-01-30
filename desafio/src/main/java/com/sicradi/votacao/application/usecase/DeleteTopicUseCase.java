package com.sicradi.votacao.application.usecase;

import com.sicradi.votacao.domain.repository.TopicRepository;
import com.sicradi.votacao.exceptions.ResourceNotFoundException;

public class DeleteTopicUseCase {
    private final TopicRepository topicRepository;

    public DeleteTopicUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public void execute(Long id) {
        if (!topicRepository.existsById(id)) {
            throw new ResourceNotFoundException("Topic not found with id " + id);
        }
        topicRepository.deleteById(id);
    }
}
