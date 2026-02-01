package com.sicredi.votacao.application.usecase.votingsession;

import java.util.List;

import com.sicredi.votacao.domain.model.Topic;
import com.sicredi.votacao.domain.model.TopicStatus;
import com.sicredi.votacao.domain.model.VotingSession;
import com.sicredi.votacao.domain.model.VotingSessionStatus;
import com.sicredi.votacao.domain.repository.VotingSessionRepository;
import com.sicredi.votacao.domain.repository.TopicRepository;
import com.sicredi.votacao.exceptions.ValidationException;
import com.sicredi.votacao.exceptions.BusinessException;
import com.sicredi.votacao.exceptions.NotFoundException;
import com.sicredi.votacao.application.config.VotingSessionProperties;


public class OpenVotingSessionUseCase {
    private final VotingSessionRepository votingSessionRepository;
    private final TopicRepository topicRepository;
    private final VotingSessionProperties votingSessionProperties;

    public OpenVotingSessionUseCase(VotingSessionRepository votingSessionRepository, TopicRepository topicRepository, VotingSessionProperties votingSessionProperties) {
        this.votingSessionRepository = votingSessionRepository;
        this.topicRepository = topicRepository;
        this.votingSessionProperties = votingSessionProperties;
    }

    public VotingSession execute(Long topicId, Integer duration) {
        if (topicId == null) {
            throw new ValidationException("O ID do tópico é obrigatório para abrir uma sessão de votação.");
        }
        if (!topicRepository.existsById(topicId)) {
            throw new NotFoundException("Tópico não encontrado para o ID informado: " + topicId);
        }

        // Só pode abrir se o Topic (pauta) estiver com status OPEN
        Topic topic = topicRepository.findById(topicId)
            .orElseThrow(() -> new NotFoundException("Tópico não encontrado para o ID informado: " + topicId));
        if (!TopicStatus.OPEN.equals(topic.getStatus())) {
            throw new BusinessException("Só é possível abrir sessão para tópicos com status OPEN.");
        }

        if (hasOpenOrUnexpiredSession(topicId)) {
            throw new BusinessException("Já existe uma sessão aberta.");
        }

        Integer sessionDuration = duration;
        if (sessionDuration == null || sessionDuration <= 0) {
            sessionDuration = votingSessionProperties.getDefaultDurationMinutes();
        }

        VotingSession session = new VotingSession(topicId, sessionDuration);
        return votingSessionRepository.save(session);
    }

    // Verifica se já existe sessão aberta ou não expirada para o tópico
    private boolean hasOpenOrUnexpiredSession(Long topicId) {
        List<VotingSession> sessions = votingSessionRepository.findAll();
        return sessions.stream()
            .filter(s -> s.getTopicId().equals(topicId))
            .anyMatch(s -> VotingSessionStatus.OPEN.equals(s.getStatus()) || !s.isExpired()); // Para expiração automática, use CheckAndCloseVotingSessionUseCase se necessário
    }
}
