package com.sicradi.votacao.interfaces.rest.dto;

import java.time.LocalDateTime;

public class VoteResponse {
    private Long id;
    private Long topicId;
    private String associateId;
    private Integer choice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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
}
