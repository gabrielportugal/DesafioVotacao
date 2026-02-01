package com.sicredi.votacao.infrastructure.persistence.mapper;

import com.sicredi.votacao.domain.model.Vote;
import com.sicredi.votacao.infrastructure.persistence.entity.VoteEntity;

public class VoteEntityMapper {

    public static VoteEntity toEntity(Vote vote) {
        if (vote == null) return null;
        VoteEntity entity = new VoteEntity();
        entity.setId(vote.getVot_id());
        entity.setTopicId(vote.getVot_topicId());
        entity.setAssociateId(vote.getVot_associateId());
        entity.setChoice(vote.getVot_choice());
        entity.setCreatedAt(vote.getVot_createdAt());
        entity.setUpdatedAt(vote.getVot_updatedAt());
        return entity;
    }

    public static Vote toDomain(VoteEntity entity) {
        if (entity == null) return null;
        Vote vote = new Vote();
        vote.setVot_id(entity.getId());
        vote.setVot_topicId(entity.getTopicId());
        vote.setVot_associateId(entity.getAssociateId());
        vote.setVot_choice(entity.getChoice());
        vote.setVot_createdAt(entity.getCreatedAt());
        vote.setVot_updatedAt(entity.getUpdatedAt());
        return vote;
    }

}
