package com.sicradi.votacao.application.usecase.votingsession;

import com.sicradi.votacao.domain.repository.VotingSessionRepository;
import com.sicradi.votacao.domain.repository.TopicRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VotingSessionUseCaseConfig {

    @Bean
    public OpenVotingSessionUseCase openVotingSessionUseCase(VotingSessionRepository votingSessionRepository,
            TopicRepository topicRepository) {
        return new OpenVotingSessionUseCase(votingSessionRepository, topicRepository);
    }

    @Bean
    public CloseVotingSessionUseCase closeVotingSessionUseCase(VotingSessionRepository votingSessionRepository) {
        return new CloseVotingSessionUseCase(votingSessionRepository);
    }

}
