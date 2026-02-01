
package com.sicredi.votacao.interfaces.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import com.sicredi.votacao.domain.model.TopicStatus;

@Getter
@Setter
@NoArgsConstructor
public class TopicResponse {

    private Long id;
    private String title;
    private String description;
    private TopicStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}
