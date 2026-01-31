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

        // Só pode abrir se o Topic (pauta) estiver com status OPEN
        Topic topic = topicRepository.findById(topicId)
            .orElseThrow(() -> new NotFoundException("Tópico não encontrado para o ID informado: " + topicId));
        if (!TopicStatus.OPEN.equals(topic.getStatus())) {
            throw new BusinessException("Só é possível abrir sessão para tópicos com status OPEN.");
        }

        if (hasOpenOrUnexpiredSession(topicId)) {
            throw new BusinessException("Já existe uma sessão aberta.");
        }

        VotingSession session = new VotingSession(topicId, duration);
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
