package com.sicradi.votacao.application.usecase.topic;

import com.sicradi.votacao.domain.repository.TopicRepository;
import com.sicradi.votacao.domain.repository.VotingSessionRepository;
import com.sicradi.votacao.domain.model.Topic;
import com.sicradi.votacao.domain.model.TopicStatus;
import com.sicradi.votacao.domain.model.VotingSession;
import com.sicradi.votacao.domain.model.VotingSessionStatus;
import com.sicradi.votacao.exceptions.ResourceNotFoundException;

public class DeleteTopicUseCase {
    private final TopicRepository topicRepository;
    private final VotingSessionRepository votingSessionRepository;

    public DeleteTopicUseCase(TopicRepository topicRepository, VotingSessionRepository votingSessionRepository) {
        this.topicRepository = topicRepository;
        this.votingSessionRepository = votingSessionRepository;
    }

    public void execute(Long id) {
        Topic topic = topicRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id " + id));
        topic.setStatus(TopicStatus.CLOSED);
        topicRepository.save(topic);

        // Fechar todas as sessões de votação relacionadas
        if (votingSessionRepository != null) {
            votingSessionRepository.findAll().stream()
                .filter(session -> session.getTopicId().equals(id))
                .forEach(session -> {
                    session.setStatus(VotingSessionStatus.CLOSED);
                    votingSessionRepository.save(session);
                });
        }
    }
}
