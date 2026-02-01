
package com.sicredi.votacao.infrastructure.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import com.sicredi.votacao.domain.model.VotingSessionStatus;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
