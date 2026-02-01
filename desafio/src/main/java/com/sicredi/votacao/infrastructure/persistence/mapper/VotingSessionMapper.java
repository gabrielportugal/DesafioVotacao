package com.sicredi.votacao.infrastructure.persistence.mapper;

import com.sicredi.votacao.domain.model.VotingSession;
import com.sicredi.votacao.infrastructure.persistence.entity.VotingSessionEntity;

public class VotingSessionMapper {

    public static VotingSession toDomain(VotingSessionEntity entity) {
        if (entity == null) return null;
        VotingSession session = new VotingSession(entity.getTopicId(), entity.getDuration());
        session.setId(entity.getId());
        session.setCreatedAt(entity.getCreatedAt());
        session.setClosedAt(entity.getClosedAt());
        session.setStatus(entity.getStatus());
        session.setUpdatedAt(entity.getUpdatedAt());
        return session;
    }

    public static VotingSessionEntity toEntity(VotingSession session) {
        if (session == null) return null;
        VotingSessionEntity entity = new VotingSessionEntity();
        entity.setId(session.getId());
        entity.setTopicId(session.getTopicId());
        entity.setCreatedAt(session.getCreatedAt());
        entity.setDuration(session.getDuration());
        entity.setClosedAt(session.getClosedAt());
        entity.setStatus(session.getStatus());
        entity.setUpdatedAt(session.getUpdatedAt());
        return entity;
    }

}
