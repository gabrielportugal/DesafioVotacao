package com.sicredi.votacao.interfaces.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TopicAndVotingSession {

    private Long topicId;
    private String title;
    private String description;
    private String status;
    private List<VotingSessionDTO> votingSessions;
    
}
