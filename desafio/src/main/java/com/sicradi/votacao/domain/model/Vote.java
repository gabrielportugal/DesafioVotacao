package com.sicradi.votacao.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Vote {
    private Long id;
    private Long topicId;
    private String associateId;
    private Integer choice; // 1 = YES, 0 = NO
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Vote(Long topicId, String associateId, Integer choice) {
        this.topicId = topicId;
        this.associateId = associateId;
        this.choice = choice;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Vote() {}

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getTopicId() { return topicId; }
    public void setTopicId(Long topicId) { this.topicId = topicId; }
    public String getAssociateId() { return associateId; }
    public void setAssociateId(String associateId) { this.associateId = associateId; }
    public Integer getChoice() { return choice; }
    public void setChoice(Integer choice) { this.choice = choice; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(id, vote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
