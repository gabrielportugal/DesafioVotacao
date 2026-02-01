package com.sicredi.votacao.infrastructure.persistence.repository;

import com.sicredi.votacao.domain.model.Vote;
import com.sicredi.votacao.domain.repository.VoteRepository;
import com.sicredi.votacao.infrastructure.persistence.entity.VoteEntity;
import com.sicredi.votacao.infrastructure.persistence.jpa.VoteJpaRepository;
import com.sicredi.votacao.infrastructure.persistence.mapper.VoteEntityMapper;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class VoteRepositoryImpl implements VoteRepository {

    @Override
    public java.util.List<Vote> findAllByTopicId(Long topicId) {
        return voteJpaRepository.findAllByTopicId(topicId)
                .stream()
                .map(VoteEntityMapper::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

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

    @Override
    public void deleteAll() {
        voteJpaRepository.deleteAll();
    }

}
