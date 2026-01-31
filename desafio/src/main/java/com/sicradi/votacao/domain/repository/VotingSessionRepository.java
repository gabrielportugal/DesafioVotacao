package com.sicradi.votacao.domain.repository;

import com.sicradi.votacao.domain.model.VotingSession;

import java.util.List;
import java.util.Optional;

public interface VotingSessionRepository {
    VotingSession save(VotingSession votingSession);
    Optional<VotingSession> findById(Long id);
    List<VotingSession> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
