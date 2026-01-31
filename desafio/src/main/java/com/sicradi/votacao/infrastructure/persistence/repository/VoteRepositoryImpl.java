package com.sicradi.votacao.infrastructure.persistence.repository;

import com.sicradi.votacao.domain.model.Vote;
import com.sicradi.votacao.domain.repository.VoteRepository;
import com.sicradi.votacao.infrastructure.persistence.entity.VoteEntity;
import com.sicradi.votacao.infrastructure.persistence.jpa.VoteJpaRepository;
import com.sicradi.votacao.infrastructure.persistence.mapper.VoteEntityMapper;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class VoteRepositoryImpl implements VoteRepository {
    private final VoteJpaRepository voteJpaRepository;

    public VoteRepositoryImpl(VoteJpaRepository voteJpaRepository) {
        this.voteJpaRepository = voteJpaRepository;
    }

    @Override
    public Vote save(Vote vote) {
        VoteEntity entity = VoteEntityMapper.toEntity(vote);
        VoteEntity saved = voteJpaRepository.save(entity);
        return VoteEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Vote> findByTopicIdAndAssociateId(Long topicId, String associateId) {
        return voteJpaRepository.findByTopicIdAndAssociateId(topicId, associateId)
                .map(VoteEntityMapper::toDomain);
    }
}
