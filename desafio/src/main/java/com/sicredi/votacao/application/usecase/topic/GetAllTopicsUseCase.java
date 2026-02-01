package com.sicredi.votacao.application.usecase.topic;

import com.sicredi.votacao.domain.model.Topic;
import com.sicredi.votacao.domain.repository.TopicRepository;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GetAllTopicsUseCase {

    private final TopicRepository topicRepository;

    public GetAllTopicsUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> execute() {
        return topicRepository.findAll();
    }

}
