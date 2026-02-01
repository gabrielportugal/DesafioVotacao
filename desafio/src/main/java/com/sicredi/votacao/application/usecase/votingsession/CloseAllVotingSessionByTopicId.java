package com.sicredi.votacao.application.usecase.votingsession;

import com.sicredi.votacao.domain.repository.VotingSessionRepository;

public class CloseAllVotingSessionByTopicId {

    private final VotingSessionRepository votingSessionRepository;
    private final CloseVotingSessionUseCase closeVotingSessionUseCase;

    public CloseAllVotingSessionByTopicId(VotingSessionRepository votingSessionRepository, CloseVotingSessionUseCase closeVotingSessionUseCase) {
        this.votingSessionRepository = votingSessionRepository;
        this.closeVotingSessionUseCase = closeVotingSessionUseCase;
    }

    public void execute(Long topicId) {
        votingSessionRepository.findAll().stream()
            .filter(session -> session.getTopicId().equals(topicId))
            .forEach(session -> closeVotingSessionUseCase.execute(session.getId()));
    }

}
