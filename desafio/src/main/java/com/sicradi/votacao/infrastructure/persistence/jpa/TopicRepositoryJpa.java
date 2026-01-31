package com.sicradi.votacao.infrastructure.persistence.jpa;

import com.sicradi.votacao.infrastructure.persistence.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepositoryJpa extends JpaRepository<TopicEntity, Long> {
}
