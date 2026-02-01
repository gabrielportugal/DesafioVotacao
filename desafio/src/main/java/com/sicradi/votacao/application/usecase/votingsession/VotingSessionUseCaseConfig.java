package com.sicradi.votacao.application.usecase.votingsession;

import com.sicradi.votacao.domain.repository.VotingSessionRepository;
import com.sicradi.votacao.application.config.VotingSessionProperties;
import com.sicradi.votacao.domain.repository.TopicRepository;
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
