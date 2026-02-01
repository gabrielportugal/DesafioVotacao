package com.sicredi.votacao.infrastructure.persistence.jpa;

import com.sicredi.votacao.infrastructure.persistence.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VoteJpaRepository extends JpaRepository<VoteEntity, Long> {
    Optional<VoteEntity> findByTopicIdAndAssociateId(Long topicId, String associateId);
    java.util.List<VoteEntity> findAllByTopicId(Long topicId);
}
