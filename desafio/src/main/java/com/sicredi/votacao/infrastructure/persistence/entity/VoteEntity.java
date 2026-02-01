
package com.sicredi.votacao.infrastructure.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vote", uniqueConstraints = @UniqueConstraint(columnNames = {"topic_id", "associate_id"}))
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vot_id")
    private Long id;

    @Column(name = "vot_topic_id", nullable = false)
    private Long topicId;

    @Column(name = "vot_associate_id", nullable = false)
    private String associateId;

    @Column(name = "vot_choice", nullable = false)
    private Integer choice;

    @Column(name = "vot_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "vot_updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
