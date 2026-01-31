
package com.sicradi.votacao.domain.model;

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
