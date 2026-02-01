
package com.sicredi.votacao.interfaces.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class VoteResponse {

    private Long id;
    private Long topicId;
    private String associateId;
    private Integer choice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}
