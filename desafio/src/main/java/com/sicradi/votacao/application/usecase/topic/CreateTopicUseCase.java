package com.sicradi.votacao.application.usecase.topic;

import org.springframework.stereotype.Service;

import com.sicradi.votacao.domain.model.Topic;
import com.sicradi.votacao.domain.repository.TopicRepository;

@Service
public class CreateTopicUseCase {

    private final TopicRepository topicRepository;

    public CreateTopicUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic execute(String title, String description) {
        Topic topic = Topic.create(title, description);
        return topicRepository.save(topic);
    }

}
