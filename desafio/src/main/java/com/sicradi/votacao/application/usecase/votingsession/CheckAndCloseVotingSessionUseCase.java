package com.sicradi.votacao.application.usecase.votingsession;

import com.sicradi.votacao.domain.model.VotingSession;
import com.sicradi.votacao.domain.model.VotingSessionStatus;
import com.sicradi.votacao.domain.repository.VotingSessionRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public class CheckAndCloseVotingSessionUseCase {
    private final VotingSessionRepository votingSessionRepository;

    public CheckAndCloseVotingSessionUseCase(VotingSessionRepository votingSessionRepository) {
        this.votingSessionRepository = votingSessionRepository;
    }

    /**
     * Garante que a sessão está consistente: se expirada, fecha e persiste, sempre retorna o estado atualizado.
     * @param session Sessão de votação
     * @return sessão consistente (aberta ou fechada)
     */
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
