package com.sicredi.votacao.interfaces.rest.mapper;

import com.sicredi.votacao.domain.model.Topic;
import com.sicredi.votacao.interfaces.rest.dto.TopicResponse;

public class TopicMapper {

    public static TopicResponse toResponse(Topic topic) {
        TopicResponse response = new TopicResponse();
        response.setId(topic.getId());
        response.setTitle(topic.getTitle());
        response.setDescription(topic.getDescription());
        response.setStatus(topic.getStatus());
        response.setCreatedAt(topic.getCreatedAt());
        response.setUpdatedAt(topic.getUpdatedAt());
        return response;
    }
    
}
