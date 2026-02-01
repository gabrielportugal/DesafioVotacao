package com.sicredi.votacao.infrastructure.persistence.mapper;

import com.sicredi.votacao.domain.model.Topic;
import com.sicredi.votacao.infrastructure.persistence.entity.TopicEntity;

public class TopicMapper {

    public static Topic toDomain(TopicEntity entity) {
        if (entity == null) return null;
        Topic topic = new Topic();
        topic.setId(entity.getId());
        topic.setTitle(entity.getTitle());
        topic.setDescription(entity.getDescription());
        topic.setStatus(entity.getStatus() == null ? null : com.sicredi.votacao.domain.model.TopicStatus.valueOf(entity.getStatus()));
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
        entity.setStatus(topic.getStatus() == null ? null : topic.getStatus().name());
        entity.setCreatedAt(topic.getCreatedAt());
        entity.setUpdatedAt(topic.getUpdatedAt());
        return entity;
    }

}
