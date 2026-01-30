package com.sicradi.votacao.infrastructure.persistence.mapper;

import com.sicradi.votacao.domain.model.Topic;
import com.sicradi.votacao.infrastructure.persistence.entity.TopicEntity;

public class TopicMapper {
    public static Topic toDomain(TopicEntity entity) {
        if (entity == null) return null;
        Topic topic = new Topic();
        topic.setId(entity.getId());
        topic.setTitle(entity.getTitle());
        topic.setDescription(entity.getDescription());
        topic.setStatus(entity.getStatus());
        topic.setCreatedAt(entity.getCreatedAt());
        topic.setUpdatedAt(entity.getUpdatedAt());
        return topic;
    }

    public static TopicEntity toEntity(Topic topic) {
        if (topic == null) return null;
        TopicEntity entity = new TopicEntity();
        entity.setId(topic.getId());
        entity.setTitle(topic.getTitle());
        entity.setDescription(topic.getDescription());
        entity.setStatus(topic.getStatus());
        entity.setCreatedAt(topic.getCreatedAt());
        entity.setUpdatedAt(topic.getUpdatedAt());
        return entity;
    }
}
