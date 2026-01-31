
package com.sicradi.votacao.domain.model;

import java.time.LocalDateTime;

public class Topic {
    private Long id;
    private String title;
    private String description;
    private TopicStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Construtores, getters, setters e regras de negócio aqui
    public Topic() {}

    public Topic(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = TopicStatus.OPEN;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TopicStatus getStatus() { return status; }
    public void setStatus(TopicStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
