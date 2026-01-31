package com.sicradi.votacao.application.usecase.topic;

import com.sicradi.votacao.domain.model.Topic;
import com.sicradi.votacao.domain.model.VotingSession;
import com.sicradi.votacao.domain.repository.TopicRepository;
import com.sicradi.votacao.domain.repository.VotingSessionRepository;
import com.sicradi.votacao.exceptions.NotFoundException;
import com.sicradi.votacao.interfaces.rest.dto.TopicAndVotingSession;
import com.sicradi.votacao.interfaces.rest.dto.VotingSessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetTopicWithVotingSessionsUseCase {
    private final TopicRepository topicRepository;
    private final VotingSessionRepository votingSessionRepository;

    public TopicAndVotingSession execute(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException("Topic not found with id: " + topicId));
        List<VotingSession> sessions = votingSessionRepository.findByTopicId(topicId);
        TopicAndVotingSession dto = new TopicAndVotingSession();
        dto.setTopicId(topic.getId());
        dto.setTitle(topic.getTitle());
        dto.setDescription(topic.getDescription());
        dto.setStatus(topic.getStatus().name());
        dto.setVotingSessions(sessions.stream().map(this::toDTO).collect(Collectors.toList()));
        return dto;
    }

    private VotingSessionDTO toDTO(VotingSession session) {
        VotingSessionDTO dto = new VotingSessionDTO();
        dto.setCreatedAt(session.getCreatedAt());
        dto.setStatus(session.getStatus().name());
        dto.setDuration(session.getDuration());
        
        if (session.getStatus() != null && !"OPEN".equals(session.getStatus().name())) {
            dto.setClosedAt(session.getClosedAt());
            dto.setClosedBy(session.getClosedBy());
        } else {
            dto.setClosedAt(null);
            dto.setClosedBy(null);
        }
        
        return dto;
    }
}