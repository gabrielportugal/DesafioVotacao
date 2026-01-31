package com.sicradi.votacao.infrastructure.persistence.entity;

import jakarta.persistence.*;

import com.sicradi.votacao.domain.model.VotingSessionStatus;
import java.time.LocalDateTime;

@Entity
@Table(name = "voting_session")
public class VotingSessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vse_id")
    private Long id;

    @Column(name = "vse_topic_id", nullable = false)
    private Long topicId;

    @Column(name = "vse_created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "vse_duration", nullable = false)
    private Integer duration;

    @Column(name = "vse_closed_at")
    private LocalDateTime closedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "vse_status", nullable = false)
    private VotingSessionStatus status;

    @Column(name = "vse_updated_at")
    private LocalDateTime updatedAt;

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getTopicId() { return topicId; }
    public void setTopicId(Long topicId) { this.topicId = topicId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public LocalDateTime getClosedAt() { return closedAt; }
    public void setClosedAt(LocalDateTime closedAt) { this.closedAt = closedAt; }
    public VotingSessionStatus getStatus() { return status; }
    public void setStatus(VotingSessionStatus status) { this.status = status; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
