package com.sicredi.votacao.infrastructure.persistence.jpa;

import com.sicredi.votacao.infrastructure.persistence.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepositoryJpa extends JpaRepository<TopicEntity, Long> {
}
