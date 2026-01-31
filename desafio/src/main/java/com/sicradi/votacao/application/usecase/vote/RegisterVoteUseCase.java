package com.sicradi.votacao.application.usecase.vote;

import com.sicradi.votacao.domain.model.Vote;
import com.sicradi.votacao.domain.repository.VoteRepository;
import com.sicradi.votacao.domain.repository.TopicRepository;
import com.sicradi.votacao.domain.repository.VotingSessionRepository;
import com.sicradi.votacao.domain.model.VotingSession;

public class RegisterVoteUseCase {
    private final VoteRepository voteRepository;
    private final TopicRepository topicRepository;
    private final VotingSessionRepository votingSessionRepository;

    public RegisterVoteUseCase(VoteRepository voteRepository, TopicRepository topicRepository, VotingSessionRepository votingSessionRepository) {
        this.voteRepository = voteRepository;
        this.topicRepository = topicRepository;
        this.votingSessionRepository = votingSessionRepository;
    }

    public Vote execute(Long topicId, String associateId, Integer choice) {
        if (topicId == null || associateId == null || choice == null) {
            throw new IllegalArgumentException("Todos os campos são obrigatórios.");
        }
        if (!topicRepository.existsById(topicId)) {
            throw new IllegalArgumentException("Tópico não encontrado.");
        }
        VotingSession session = votingSessionRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Sessão de votação não encontrada para o tópico."));
        if (!session.getStatus().name().equals("OPEN") || session.isExpired()) {
            throw new IllegalArgumentException("Sessão de votação não está aberta ou já expirou.");
        }
        if (voteRepository.findByTopicIdAndAssociateId(topicId, associateId).isPresent()) {
            throw new IllegalArgumentException("Associado já votou neste tópico.");
        }
        if (choice != 0 && choice != 1) {
            throw new IllegalArgumentException("Escolha inválida. Use 1 para SIM ou 0 para NÃO.");
        }
        Vote vote = new Vote(topicId, associateId, choice);
        return voteRepository.save(vote);
    }
}
