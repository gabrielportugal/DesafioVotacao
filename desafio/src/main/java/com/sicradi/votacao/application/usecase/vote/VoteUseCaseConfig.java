package com.sicradi.votacao.application.usecase.vote;

import com.sicradi.votacao.domain.repository.VoteRepository;
import com.sicradi.votacao.domain.repository.TopicRepository;
import com.sicradi.votacao.domain.repository.VotingSessionRepository;
import com.sicradi.votacao.application.usecase.votingsession.CheckAndCloseVotingSessionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoteUseCaseConfig {

    @Bean
    public RegisterVoteUseCase registerVoteUseCase(VoteRepository voteRepository, TopicRepository topicRepository, VotingSessionRepository votingSessionRepository, CheckAndCloseVotingSessionUseCase checkAndCloseVotingSessionUseCase) {
        return new RegisterVoteUseCase(voteRepository, topicRepository, votingSessionRepository, checkAndCloseVotingSessionUseCase);
    }

}
