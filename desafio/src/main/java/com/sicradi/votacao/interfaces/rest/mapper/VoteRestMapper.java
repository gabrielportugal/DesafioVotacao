package com.sicradi.votacao.interfaces.rest.mapper;

import com.sicradi.votacao.domain.model.Vote;
import com.sicradi.votacao.interfaces.rest.dto.VoteResponse;

public class VoteRestMapper {

    public static VoteResponse toResponse(Vote vote) {
        VoteResponse response = new VoteResponse();
        response.setId(vote.getId());
        response.setTopicId(vote.getTopicId());
        response.setAssociateId(vote.getAssociateId());
        response.setChoice(vote.getChoice());
        response.setCreatedAt(vote.getCreatedAt());
        response.setUpdatedAt(vote.getUpdatedAt());
        return response;
    }
    
}
