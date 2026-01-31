package com.sicradi.votacao.application.usecase.topic;

import com.sicradi.votacao.domain.repository.TopicRepository;
import com.sicradi.votacao.domain.repository.VotingSessionRepository;
import com.sicradi.votacao.application.usecase.votingsession.CloseAllVotingSessionByTopicId;
import com.sicradi.votacao.domain.model.Topic;
import com.sicradi.votacao.domain.model.TopicStatus;
import com.sicradi.votacao.exceptions.NotFoundException;

public class DeleteTopicUseCase {

    private final TopicRepository topicRepository;
    private final VotingSessionRepository votingSessionRepository;
    private final CloseAllVotingSessionByTopicId closeAllVotingSessionByTopicId;

    public DeleteTopicUseCase(TopicRepository topicRepository, VotingSessionRepository votingSessionRepository) {
        this.topicRepository = topicRepository;
        this.votingSessionRepository = votingSessionRepository;
        this.closeAllVotingSessionByTopicId = new CloseAllVotingSessionByTopicId(votingSessionRepository);
    }

    public void execute(Long id) {
        Topic topic = topicRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Tópico não encontrado para o ID informado: " + id));
        topic.setStatus(TopicStatus.CLOSED);
        topicRepository.save(topic);

        // Fechar todas as sessões de votação relacionadas
        closeAllVotingSessionByTopicId.execute(id);
    }
}
