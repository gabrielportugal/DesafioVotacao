package com.sicredi.votacao.application.usecase.topic;

import com.sicredi.votacao.domain.repository.TopicRepository;
import com.sicredi.votacao.domain.repository.VotingSessionRepository;
import com.sicredi.votacao.domain.repository.VoteRepository;
import com.sicredi.votacao.application.usecase.votingsession.CloseAllVotingSessionByTopicId;
import com.sicredi.votacao.application.usecase.votingsession.CloseVotingSessionUseCase;
import com.sicredi.votacao.application.usecase.votingsession.CheckAndCloseVotingSessionUseCase;
    
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicUseCaseConfig {

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
    public CloseAllVotingSessionByTopicId closeAllVotingSessionByTopicId(VotingSessionRepository votingSessionRepository, CloseVotingSessionUseCase closeVotingSessionUseCase) {
        return new CloseAllVotingSessionByTopicId(votingSessionRepository, closeVotingSessionUseCase);
    }

    @Bean
    public DeleteTopicUseCase deleteTopicUseCase(TopicRepository topicRepository, CloseAllVotingSessionByTopicId closeAllVotingSessionByTopicId) {
        return new DeleteTopicUseCase(topicRepository, closeAllVotingSessionByTopicId);
    }

    @Bean
    public GetVoteResultUseCase getVoteResultUseCase(VoteRepository voteRepository, TopicRepository topicRepository) {
        return new GetVoteResultUseCase(voteRepository, topicRepository);
    }

    @Bean
    public GetTopicWithVotingSessionsUseCase getTopicWithVotingSessionsUseCase(TopicRepository topicRepository, VotingSessionRepository votingSessionRepository, CheckAndCloseVotingSessionUseCase checkAndCloseVotingSessionUseCase) {
        return new GetTopicWithVotingSessionsUseCase(topicRepository, votingSessionRepository, checkAndCloseVotingSessionUseCase);
    }

}
