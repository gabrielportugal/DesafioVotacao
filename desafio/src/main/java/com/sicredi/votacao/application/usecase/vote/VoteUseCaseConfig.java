package com.sicredi.votacao.application.usecase.vote;

import com.sicredi.votacao.domain.repository.VoteRepository;
import com.sicredi.votacao.domain.repository.TopicRepository;
import com.sicredi.votacao.domain.repository.VotingSessionRepository;
import com.sicredi.votacao.application.usecase.votingsession.CheckAndCloseVotingSessionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoteUseCaseConfig {

    @Bean
    public RegisterVoteUseCase registerVoteUseCase(VoteRepository voteRepository, TopicRepository topicRepository, VotingSessionRepository votingSessionRepository, CheckAndCloseVotingSessionUseCase checkAndCloseVotingSessionUseCase) {
        return new RegisterVoteUseCase(voteRepository, topicRepository, votingSessionRepository, checkAndCloseVotingSessionUseCase);
    }

}
