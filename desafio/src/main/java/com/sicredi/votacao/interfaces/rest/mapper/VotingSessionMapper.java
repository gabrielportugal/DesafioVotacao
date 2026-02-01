package com.sicredi.votacao.interfaces.rest.mapper;

import com.sicredi.votacao.domain.model.VotingSession;
import com.sicredi.votacao.interfaces.rest.dto.VotingSessionResponse;

public class VotingSessionMapper {

    public static VotingSessionResponse toResponse(VotingSession session) {
        VotingSessionResponse response = new VotingSessionResponse();
        response.setId(session.getId());
        response.setTopicId(session.getTopicId());
        response.setDuration(session.getDuration());
        response.setStatus(session.getStatus().name());
        response.setCreatedAt(session.getCreatedAt());
        response.setClosedAt(session.getClosedAt());
        response.setUpdatedAt(session.getUpdatedAt());
        return response;
    }
    
}
