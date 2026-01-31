package com.sicradi.votacao.domain.model;

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

    public Topic(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = TopicStatus.OPEN;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}