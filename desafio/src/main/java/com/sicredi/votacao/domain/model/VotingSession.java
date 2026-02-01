
package com.sicredi.votacao.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
