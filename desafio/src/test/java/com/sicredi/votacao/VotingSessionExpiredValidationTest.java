package com.sicredi.votacao;

import com.sicredi.votacao.interfaces.rest.v1.TopicController;
import com.sicredi.votacao.interfaces.rest.v1.VotingSessionController;
import com.sicredi.votacao.utils.TestDatabaseCleaner;
import com.sicredi.votacao.interfaces.rest.v1.VoteController;
import com.sicredi.votacao.interfaces.rest.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.AfterEach;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class VotingSessionExpiredValidationTest {

    @Autowired
    private TopicController topicController;
    @Autowired
    private VotingSessionController votingSessionController;
    @Autowired
    private VoteController voteController;

    @Autowired
    private TestDatabaseCleaner testDatabaseCleaner;

    @AfterEach
    void cleanDatabaseAfterEach() {
        testDatabaseCleaner.cleanDatabase();
        System.out.println("\uD83E\uDEB9 Banco de dados limpo após o teste");
    }

    @Test
    void deveValidarSessaoExpirada() throws InterruptedException {
        // Cria um tópico
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setTitle("Pauta Expirada");
        topicRequest.setDescription("Descrição para sessão expirada");
        ResponseEntity<TopicResponse> topicResponse = topicController.createTopic(topicRequest);
        assertThat(topicResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        TopicResponse topic = topicResponse.getBody();
        assertThat(topic).isNotNull();
        // Abre sessão de 1 minuto (menor valor possível)
        VotingSessionRequest sessionRequest = new VotingSessionRequest();
        sessionRequest.setTopicId(topic.getId());
        sessionRequest.setDuration(1); // 1 minuto
        ResponseEntity<VotingSessionResponse> sessionResponse = votingSessionController.openSession(sessionRequest);
        assertThat(sessionResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        // Aguarda expiração (espera 65 segundos para garantir expiração do minuto)
        System.out.println("Aguardando expiração da sessão (65s)...");
        Thread.sleep(65000);
        System.out.println("Tempo de espera concluído. Tentando registrar voto em sessão expirada...");
        // Tenta votar
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setTopicId(topic.getId());
        voteRequest.setAssociateId("52998224725"); // CPF válido
        voteRequest.setChoice("Sim");
        try {
            voteController.registerVote(voteRequest);
            assertThat(false).as("Deveria lançar exceção de sessão expirada").isTrue();
        } catch (Exception ex) {
            // Espera mensagem padronizada do UseCase
            assertThat(ex.getMessage()).contains("Sessão de votação não está aberta ou já expirou.");
        }
    }
}
