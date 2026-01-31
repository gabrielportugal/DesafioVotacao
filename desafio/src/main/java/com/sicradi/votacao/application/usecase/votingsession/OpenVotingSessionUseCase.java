package com.sicradi.votacao.application.usecase.votingsession;

import com.sicradi.votacao.domain.model.VotingSession;
import com.sicradi.votacao.domain.repository.VotingSessionRepository;
import com.sicradi.votacao.domain.repository.TopicRepository;
import com.sicradi.votacao.exceptions.TopicIdRequiredException;
import com.sicradi.votacao.exceptions.TopicNotFoundException;


public class OpenVotingSessionUseCase {
    private final VotingSessionRepository votingSessionRepository;
    private final TopicRepository topicRepository;

    public OpenVotingSessionUseCase(VotingSessionRepository votingSessionRepository, TopicRepository topicRepository) {
        this.votingSessionRepository = votingSessionRepository;
        this.topicRepository = topicRepository;
    }

    public VotingSession execute(Long topicId, Integer duration) {
        if (topicId == null) {
            throw new TopicIdRequiredException();
        }
        if (!topicRepository.existsById(topicId)) {
            throw new TopicNotFoundException(topicId);
        }
        VotingSession session = new VotingSession(topicId, duration);
        return votingSessionRepository.save(session);
    }
}
