package com.sicradi.votacao.application.usecase.votingsession;

import com.sicradi.votacao.domain.repository.VotingSessionRepository;
import com.sicradi.votacao.domain.model.VotingSessionStatus;

public class CloseAllVotingSessionByTopicId {
    private final VotingSessionRepository votingSessionRepository;

    public CloseAllVotingSessionByTopicId(VotingSessionRepository votingSessionRepository) {
        this.votingSessionRepository = votingSessionRepository;
    }

    public void execute(Long topicId) {
        votingSessionRepository.findAll().stream()
            .filter(session -> session.getTopicId().equals(topicId))
            .forEach(session -> {
                session.setStatus(VotingSessionStatus.CLOSED);
                votingSessionRepository.save(session);
            });
    }
}
