package com.sicredi.votacao.application.usecase.votingsession;

import com.sicredi.votacao.domain.model.VotingSession;
import com.sicredi.votacao.domain.model.VotingSessionStatus;
import com.sicredi.votacao.domain.repository.VotingSessionRepository;
import java.time.LocalDateTime;

public class CheckAndCloseVotingSessionUseCase {
    private final VotingSessionRepository votingSessionRepository;

    public CheckAndCloseVotingSessionUseCase(VotingSessionRepository votingSessionRepository) {
        this.votingSessionRepository = votingSessionRepository;
    }

    public VotingSession checkAndCloseIfExpired(VotingSession session) {
        if (session.isExpired() && session.getStatus() != VotingSessionStatus.CLOSED) {
            session.setStatus(VotingSessionStatus.CLOSED);
            session.setClosedAt(LocalDateTime.now());
            session.setUpdatedAt(LocalDateTime.now());
            return votingSessionRepository.save(session);
        }
        return session;
    }

}
