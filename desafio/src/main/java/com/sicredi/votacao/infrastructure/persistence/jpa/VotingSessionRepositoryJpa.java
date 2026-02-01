package com.sicredi.votacao.infrastructure.persistence.jpa;

import com.sicredi.votacao.infrastructure.persistence.entity.VotingSessionEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingSessionRepositoryJpa extends JpaRepository<VotingSessionEntity, Long> {
	// Busca todas as sessões abertas para o tópico, ordenadas da mais recente para a mais antiga
	List<VotingSessionEntity> findByTopicIdAndStatusOrderByCreatedAtDesc(Long topicId, com.sicredi.votacao.domain.model.VotingSessionStatus status);

	// Busca todas as sessões para o tópico
	List<VotingSessionEntity> findByTopicId(Long topicId);

	// Busca todas as sessões abertas para o tópico, ordenadas da mais recente para a mais antiga (para uso genérico)
	default List<VotingSessionEntity> findOpenNotExpiredByTopicIdOrderByCreatedAtDesc(Long topicId) {
		return findByTopicIdAndStatusOrderByCreatedAtDesc(topicId, com.sicredi.votacao.domain.model.VotingSessionStatus.OPEN);
	}
}
