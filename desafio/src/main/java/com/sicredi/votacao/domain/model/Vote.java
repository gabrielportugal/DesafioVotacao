
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
public class Vote {

    private Long vot_id;
    private Long vot_topicId;
    private String vot_associateId;
    private Integer vot_choice; // 1 = YES, 0 = NO
    private LocalDateTime vot_createdAt;
    private LocalDateTime vot_updatedAt;

    public Vote(Long vot_topicId, String vot_associateId, Integer vot_choice) {
        this.vot_topicId = vot_topicId;
        this.vot_associateId = vot_associateId;
        this.vot_choice = vot_choice;
        this.vot_createdAt = LocalDateTime.now();
        this.vot_updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(vot_id, vote.vot_id);
    }

}
