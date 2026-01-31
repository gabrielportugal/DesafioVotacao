package com.sicradi.votacao.application.usecase.votingsession;

import java.util.List;

import com.sicradi.votacao.domain.model.Topic;
import com.sicradi.votacao.domain.model.TopicStatus;
import com.sicradi.votacao.domain.model.VotingSession;
import com.sicradi.votacao.domain.model.VotingSessionStatus;
import com.sicradi.votacao.domain.repository.VotingSessionRepository;
import com.sicradi.votacao.domain.repository.TopicRepository;
import com.sicradi.votacao.exceptions.ValidationException;
import com.sicradi.votacao.exceptions.BusinessException;
import com.sicradi.votacao.exceptions.NotFoundException;


public class OpenVotingSessionUseCase {
    private final VotingSessionRepository votingSessionRepository;
    private final TopicRepository topicRepository;

    public OpenVotingSessionUseCase(VotingSessionRepository votingSessionRepository, TopicRepository topicRepository) {
        this.votingSessionRepository = votingSessionRepository;
        this.topicRepository = topicRepository;
    }

    public VotingSession execute(Long topicId, Integer duration) {
        if (topicId == null) {
            throw new ValidationException("O ID do tópico é obrigatório para abrir uma sessão de votação.");
        }
        if (!topicRepository.existsById(topicId)) {
            throw new NotFoundException("Tópico não encontrado para o ID informado: " + topicId);
        }

        // Só pode abrir se o tópico estiver com status OPEN
        Topic topic = topicRepository.findById(topicId)
            .orElseThrow(() -> new NotFoundException("Tópico não encontrado para o ID informado: " + topicId));
        if (!TopicStatus.OPEN.equals(topic.getStatus())) {
            throw new BusinessException("Só é possível abrir sessão para tópicos com status OPEN.");
        }

        // Regra: só pode abrir se não houver sessão aberta ou expirada para o tópico
        List<VotingSession> sessions = votingSessionRepository.findAll();
        boolean hasOpenOrExpired = sessions.stream()
            .filter(s -> s.getTopicId().equals(topicId))
            .anyMatch(s -> VotingSessionStatus.OPEN.equals(s.getStatus()) || !s.isExpired());
        if (hasOpenOrExpired) {
            throw new BusinessException("Já existe uma sessão aberta.");
        }

        VotingSession session = new VotingSession(topicId, duration);
        return votingSessionRepository.save(session);
    }
}
