package com.sicradi.votacao.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class VotingSession {
    private Long id;
    private Long topicId;
    private LocalDateTime createdAt;
    private Integer duration; // minutos
    private LocalDateTime closedAt;
    private VotingSessionStatus status;
    private LocalDateTime updatedAt;

    public VotingSession(Long topicId, Integer duration) {
        this.topicId = topicId;
        this.createdAt = LocalDateTime.now();
        this.duration = (duration == null || duration <= 0) ? 1 : duration;
        this.status = VotingSessionStatus.OPEN;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isExpired() {
        if (createdAt == null || duration == null) return false;
        LocalDateTime expiration = createdAt.plusMinutes(duration);
        return LocalDateTime.now().isAfter(expiration);
    }

    public void close() {
        this.status = VotingSessionStatus.CLOSED;
        this.closedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotingSession that = (VotingSession) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
