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

  public RegisterVoteUseCase(VoteRepository voteRepository, TopicRepository topicRepository,
      VotingSessionRepository votingSessionRepository) {
    this.voteRepository = voteRepository;
    this.topicRepository = topicRepository;
    this.votingSessionRepository = votingSessionRepository;
  }

  public Vote execute(Long topicId, String associateId, String choiceRaw) {
    if (topicId == null || associateId == null || choiceRaw == null) {
      throw new IllegalArgumentException("Todos os campos são obrigatórios.");
    }

    // Conversão de 'Sim'/'Não' para 1/0
    String normalized = choiceRaw.trim().toLowerCase();
    Integer choice;
    if ("sim".equals(normalized)) {
      choice = 1;
    } else if ("não".equals(normalized) || "nao".equals(normalized)) {
      choice = 0;
    } else {
      try {
        choice = Integer.valueOf(choiceRaw);
      } catch (Exception e) {
        throw new IllegalArgumentException("Escolha inválida. Use 'Sim', 'Não', 1 ou 0.");
      }
    }

    if (!topicRepository.existsById(topicId)) {
      throw new IllegalArgumentException("Tópico não encontrado.");
    }

    VotingSession session = votingSessionRepository.findMostRecentOpenByTopicId(topicId)
      .orElseThrow(() -> new IllegalArgumentException("Nenhuma sessão de votação aberta e não expirada encontrada para o tópico."));

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
