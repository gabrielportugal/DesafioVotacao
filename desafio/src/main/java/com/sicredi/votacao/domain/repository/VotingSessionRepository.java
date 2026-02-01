package com.sicredi.votacao.domain.repository;

import com.sicredi.votacao.domain.model.VotingSession;

import java.util.List;
import java.util.Optional;

public interface VotingSessionRepository {

    VotingSession save(VotingSession votingSession);
    Optional<VotingSession> findById(Long id);
    Optional<VotingSession> findMostRecentOpenByTopicId(Long topicId);
    List<VotingSession> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<VotingSession> findByTopicId(Long topicId);
    void deleteAll();
    
}
