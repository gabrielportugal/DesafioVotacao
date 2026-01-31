package com.sicradi.votacao.application.usecase.topic;

import com.sicradi.votacao.domain.repository.TopicRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfigTopic {
    @Bean
    public CreateTopicUseCase createTopicUseCase(TopicRepository topicRepository) {
        return new CreateTopicUseCase(topicRepository);
    }

    @Bean
    public GetAllTopicsUseCase getAllTopicsUseCase(TopicRepository topicRepository) {
        return new GetAllTopicsUseCase(topicRepository);
    }

    @Bean
    public GetTopicByIdUseCase getTopicByIdUseCase(TopicRepository topicRepository) {
        return new GetTopicByIdUseCase(topicRepository);
    }

    @Bean
    public DeleteTopicUseCase deleteTopicUseCase(TopicRepository topicRepository) {
        return new DeleteTopicUseCase(topicRepository);
    }
}
