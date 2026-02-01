package com.sicredi.votacao.application.usecase.votingsession;

import com.sicredi.votacao.domain.repository.VotingSessionRepository;
import com.sicredi.votacao.application.config.VotingSessionProperties;
import com.sicredi.votacao.domain.repository.TopicRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VotingSessionUseCaseConfig {

    @Bean
    public OpenVotingSessionUseCase openVotingSessionUseCase(
            VotingSessionRepository votingSessionRepository,
            TopicRepository topicRepository,
            VotingSessionProperties votingSessionProperties) {
        return new OpenVotingSessionUseCase(votingSessionRepository, topicRepository, votingSessionProperties);
    }

    @Bean
    public CloseVotingSessionUseCase closeVotingSessionUseCase(VotingSessionRepository votingSessionRepository) {
        return new CloseVotingSessionUseCase(votingSessionRepository);
    }

    @Bean
    public CheckAndCloseVotingSessionUseCase checkAndCloseVotingSessionUseCase(VotingSessionRepository votingSessionRepository) {
        return new CheckAndCloseVotingSessionUseCase(votingSessionRepository);
    }
    
}
