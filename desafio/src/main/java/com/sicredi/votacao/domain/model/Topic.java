package com.sicredi.votacao.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    private Long id;
    private String title;
    private String description;
    private TopicStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Topic create(String title, String description) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Título não pode ser vazio");
        }
        Topic topic = new Topic();
        topic.title = title;
        topic.description = description;
        topic.status = TopicStatus.OPEN;
        topic.createdAt = LocalDateTime.now();
        topic.updatedAt = LocalDateTime.now();
        return topic;
    }

}