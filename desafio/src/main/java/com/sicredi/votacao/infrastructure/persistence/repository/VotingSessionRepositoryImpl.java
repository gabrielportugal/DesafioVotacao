package com.sicredi.votacao.infrastructure.persistence.repository;

import com.sicredi.votacao.domain.model.VotingSession;
import com.sicredi.votacao.domain.repository.VotingSessionRepository;
import com.sicredi.votacao.infrastructure.persistence.entity.VotingSessionEntity;
import com.sicredi.votacao.infrastructure.persistence.jpa.VotingSessionRepositoryJpa;
import com.sicredi.votacao.infrastructure.persistence.mapper.VotingSessionMapper;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class VotingSessionRepositoryImpl implements VotingSessionRepository {

    @Override
    public Optional<VotingSession> findMostRecentOpenByTopicId(Long topicId) {
        return jpaRepository.findOpenNotExpiredByTopicIdOrderByCreatedAtDesc(topicId)
            .stream()
            .filter(entity -> !VotingSessionMapper.toDomain(entity).isExpired()) // Para expiração automática, use CheckAndCloseVotingSessionUseCase se necessário
            .findFirst()
            .map(VotingSessionMapper::toDomain);
    }
    private final VotingSessionRepositoryJpa jpaRepository;

    public VotingSessionRepositoryImpl(VotingSessionRepositoryJpa jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public VotingSession save(VotingSession votingSession) {
        VotingSessionEntity entity = VotingSessionMapper.toEntity(votingSession);
        VotingSessionEntity saved = jpaRepository.save(entity);
        return VotingSessionMapper.toDomain(saved);
    }

    @Override
    public Optional<VotingSession> findById(Long id) {
        return jpaRepository.findById(id).map(VotingSessionMapper::toDomain);
    }

    @Override
    public List<VotingSession> findAll() {
        return jpaRepository.findAll().stream().map(VotingSessionMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public List<VotingSession> findByTopicId(Long topicId) {
        return jpaRepository.findByTopicId(topicId)
                .stream()
                .map(VotingSessionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        jpaRepository.deleteAll();
    }

}
