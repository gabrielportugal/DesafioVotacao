package com.sicradi.votacao.infrastructure.persistence.mapper;

import com.sicradi.votacao.domain.model.Vote;
import com.sicradi.votacao.infrastructure.persistence.entity.VoteEntity;

public class VoteEntityMapper {

    public static VoteEntity toEntity(Vote vote) {
        if (vote == null) return null;
        VoteEntity entity = new VoteEntity();
        entity.setId(vote.getId());
        entity.setTopicId(vote.getTopicId());
        entity.setAssociateId(vote.getAssociateId());
        entity.setChoice(vote.getChoice());
        entity.setCreatedAt(vote.getCreatedAt());
        entity.setUpdatedAt(vote.getUpdatedAt());
        return entity;
    }

    public static Vote toDomain(VoteEntity entity) {
        if (entity == null) return null;
        Vote vote = new Vote();
        vote.setId(entity.getId());
        vote.setTopicId(entity.getTopicId());
        vote.setAssociateId(entity.getAssociateId());
        vote.setChoice(entity.getChoice());
        vote.setCreatedAt(entity.getCreatedAt());
        vote.setUpdatedAt(entity.getUpdatedAt());
        return vote;
    }

}
