package com.sicradi.votacao.application.usecase.topic;

import com.sicradi.votacao.domain.model.Topic;
import com.sicradi.votacao.domain.repository.TopicRepository;
import java.util.List;

public class GetAllTopicsUseCase {
    private final TopicRepository topicRepository;

    public GetAllTopicsUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> execute() {
        return topicRepository.findAll();
    }
}
