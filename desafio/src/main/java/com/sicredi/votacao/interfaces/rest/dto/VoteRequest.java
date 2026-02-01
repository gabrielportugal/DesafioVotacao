
package com.sicredi.votacao.interfaces.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class VoteRequest {

    private Long topicId;
    private String associateId;
    private String choice;
    
}
