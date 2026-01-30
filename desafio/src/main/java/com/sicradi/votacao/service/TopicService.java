package com.sicradi.votacao.service;

import com.sicradi.votacao.repository.TopicRepository;
import com.sicradi.votacao.domain.model.Topic;
import com.sicradi.votacao.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
  private final TopicRepository topicRepository;

  public TopicService(TopicRepository topicRepository) {
    this.topicRepository = topicRepository;
  }

  // Retorna todas as pautas
  public List<Topic> getAllTopics() {
    return this.topicRepository.findAll();
  }

  // Retorna uma pauta por ID
  public Topic getTopicById(Long id) {
    Optional<Topic> topic = this.topicRepository.findById(id);
    return topic.orElseThrow(() -> new ResourceNotFoundException("Topic not found with id " + id));
  }

  // Cria uma nova pauta
  public Topic createTopic(Topic topic) {
    return this.topicRepository.save(topic);
  }

  // Deleta uma pauta por ID
  public void deleteTopic(Long id) {
    if (!this.topicRepository.existsById(id)) {
      throw new ResourceNotFoundException("Topic not found with id " + id);
    }

    this.topicRepository.deleteById(id);
  }

  // Abre uma sessão de votação para uma pauta
  public String openVotingSession(String topicId) {
    // ficar em VotingSessionService
    return "Abrindo sessão de votação ainda não implementado";
  }

  // Verifica se a sessão de votação está aberta
  public String isVotingSessionOpen(String topicId) {
    // ficar em VotingSessionService
    return "Sessão de votação ainda não implementada";
  }

  // Registra um voto
  public String registerVote(String topicId, String associateId, String vote) {
    // ficar em VotingService
    return "Registrando voto ainda não implementado";
  }

  // Obtém o resultado da votação
  public String getVotingResult(String topicId) {
    // ficar em VotingSessionService
    return "Resultado não implementado";
  }
}
