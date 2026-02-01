package com.sicredi.votacao.interfaces.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class VotingSessionDTO {

    private String status;
    private LocalDateTime createdAt;
    private Integer duration;
    private LocalDateTime closedAt;
    
}
