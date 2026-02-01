
package com.sicredi.votacao.interfaces.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class VotingSessionResponse {

    private Long id;
    private Long topicId;
    private Integer duration;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    private LocalDateTime updatedAt;
    
}
