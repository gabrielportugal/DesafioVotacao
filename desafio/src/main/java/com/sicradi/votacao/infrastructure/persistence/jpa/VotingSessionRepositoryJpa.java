package com.sicradi.votacao.infrastructure.persistence.jpa;

import com.sicradi.votacao.infrastructure.persistence.entity.VotingSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingSessionRepositoryJpa extends JpaRepository<VotingSessionEntity, Long> {
}
