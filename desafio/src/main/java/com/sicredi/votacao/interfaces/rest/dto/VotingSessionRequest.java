
package com.sicredi.votacao.interfaces.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class VotingSessionRequest {

    private Long topicId;
    private Integer duration;
    
}
