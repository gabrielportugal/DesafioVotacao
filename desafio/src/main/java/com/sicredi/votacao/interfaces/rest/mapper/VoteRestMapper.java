package com.sicredi.votacao.interfaces.rest.mapper;

import com.sicredi.votacao.domain.model.Vote;
import com.sicredi.votacao.interfaces.rest.dto.VoteResponse;

public class VoteRestMapper {

    public static VoteResponse toResponse(Vote vote) {
        VoteResponse response = new VoteResponse();
        response.setId(vote.getVot_id());
        response.setTopicId(vote.getVot_topicId());
        response.setAssociateId(vote.getVot_associateId());
        response.setChoice(vote.getVot_choice());
        response.setCreatedAt(vote.getVot_createdAt());
        response.setUpdatedAt(vote.getVot_updatedAt());
        return response;
    }
    
}
