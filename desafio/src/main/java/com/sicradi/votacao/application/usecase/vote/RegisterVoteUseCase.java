package com.sicradi.votacao.application.usecase.vote;

import com.sicradi.votacao.domain.model.Vote;
import com.sicradi.votacao.domain.repository.VoteRepository;
import com.sicradi.votacao.domain.repository.TopicRepository;
import com.sicradi.votacao.domain.repository.VotingSessionRepository;
import com.sicradi.votacao.domain.model.VotingSession;
import com.sicradi.votacao.domain.model.VotingSessionStatus;
import com.sicradi.votacao.application.usecase.votingsession.CheckAndCloseVotingSessionUseCase;
import com.sicradi.votacao.exceptions.ValidationException;
import com.sicradi.votacao.exceptions.NotFoundException;
import com.sicradi.votacao.exceptions.BusinessException;

public class RegisterVoteUseCase {

    private final VoteRepository voteRepository;
    private final TopicRepository topicRepository;
    private final VotingSessionRepository votingSessionRepository;
    private final CheckAndCloseVotingSessionUseCase checkAndCloseVotingSessionUseCase;

    public RegisterVoteUseCase(VoteRepository voteRepository, TopicRepository topicRepository,
                               VotingSessionRepository votingSessionRepository,
                               CheckAndCloseVotingSessionUseCase checkAndCloseVotingSessionUseCase) {
        this.voteRepository = voteRepository;
        this.topicRepository = topicRepository;
        this.votingSessionRepository = votingSessionRepository;
        this.checkAndCloseVotingSessionUseCase = checkAndCloseVotingSessionUseCase;
    }

    public Vote execute(Long topicId, String associateId, String choiceRaw) {
        if (topicId == null || associateId == null || choiceRaw == null) {
            throw new ValidationException("Todos os campos são obrigatórios.");
        }

        Integer choice = parseChoice(choiceRaw);

        if (!topicRepository.existsById(topicId)) {
            throw new NotFoundException("Tópico não encontrado.");
        }

        VotingSession votingSession = votingSessionRepository.findMostRecentOpenByTopicId(topicId)
            .map(checkAndCloseVotingSessionUseCase::checkAndCloseIfExpired)
            .orElseThrow(() -> new BusinessException("Sessão de votação não está aberta ou já expirou."));
        if (votingSession.isExpired() || votingSession.getStatus() != VotingSessionStatus.OPEN) {
            throw new BusinessException("Sessão de votação não está aberta ou já expirou.");
        }

        if (voteRepository.findByTopicIdAndAssociateId(topicId, associateId).isPresent()) {
            throw new BusinessException("Associado já votou neste tópico.");
        }

        Vote vote = new Vote(topicId, associateId, choice);
        return voteRepository.save(vote);
    }

    // Conversão e validação da escolha do voto
    private Integer parseChoice(String choiceRaw) {
        String normalized = choiceRaw.trim().toLowerCase();
        if ("sim".equals(normalized)) {
            return 1;
        } else if ("não".equals(normalized) || "nao".equals(normalized)) {
            return 0;
        } else {
            try {
                return Integer.valueOf(choiceRaw);
            } catch (Exception e) {
                throw new ValidationException("Escolha inválida. Use 'Sim', 'Não', 1 ou 0.");
            }
        }
    }
  
}
