package com.sicredi.votacao.application.usecase.votingsession;

import com.sicredi.votacao.domain.model.VotingSession;
import com.sicredi.votacao.domain.repository.VotingSessionRepository;
import java.util.Optional;
import com.sicredi.votacao.exceptions.NotFoundException;

public class CloseVotingSessionUseCase {

    private final VotingSessionRepository votingSessionRepository;

    public CloseVotingSessionUseCase(VotingSessionRepository votingSessionRepository) {
        this.votingSessionRepository = votingSessionRepository;
    }

    public VotingSession execute(Long votingSessionId) {
        Optional<VotingSession> optional = votingSessionRepository.findById(votingSessionId);
        if (optional.isEmpty()) {
            throw new NotFoundException("Sessão de votação não encontrada para o ID informado: " + votingSessionId);
        }
        VotingSession session = optional.get();
        session.close();
        return votingSessionRepository.save(session);
    }

}
