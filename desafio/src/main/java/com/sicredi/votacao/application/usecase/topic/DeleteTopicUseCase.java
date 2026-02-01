package com.sicredi.votacao.application.usecase.topic;

import com.sicredi.votacao.domain.repository.TopicRepository;
import com.sicredi.votacao.application.usecase.votingsession.CloseAllVotingSessionByTopicId;
import com.sicredi.votacao.domain.model.Topic;
import com.sicredi.votacao.domain.model.TopicStatus;
import com.sicredi.votacao.exceptions.NotFoundException;

import org.springframework.stereotype.Service;

@Service
public class DeleteTopicUseCase {

    private final TopicRepository topicRepository;
    private final CloseAllVotingSessionByTopicId closeAllVotingSessionByTopicId;

    public DeleteTopicUseCase(TopicRepository topicRepository, CloseAllVotingSessionByTopicId closeAllVotingSessionByTopicId) {
        this.topicRepository = topicRepository;
        this.closeAllVotingSessionByTopicId = closeAllVotingSessionByTopicId;
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
