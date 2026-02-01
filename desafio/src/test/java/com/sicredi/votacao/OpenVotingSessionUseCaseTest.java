package com.sicredi.votacao;

import com.sicredi.votacao.interfaces.rest.v1.TopicController;
import com.sicredi.votacao.interfaces.rest.v1.VotingSessionController;
import com.sicredi.votacao.utils.TestDatabaseCleaner;
import com.sicredi.votacao.interfaces.rest.dto.TopicRequest;
import com.sicredi.votacao.interfaces.rest.dto.TopicResponse;
import com.sicredi.votacao.interfaces.rest.dto.VotingSessionRequest;
import com.sicredi.votacao.interfaces.rest.dto.VotingSessionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.AfterEach;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class OpenVotingSessionUseCaseTest {

    @Autowired
    private TopicController topicController;
    @Autowired
    private VotingSessionController votingSessionController;

    @Autowired
    private TestDatabaseCleaner testDatabaseCleaner;

    @AfterEach
    void cleanDatabaseAfterEach() {
        testDatabaseCleaner.cleanDatabase();
        System.out.println("\uD83E\uDEB9 Banco de dados limpo após o teste");
    }

    @Test
    void deveAbrirSessaoDeVotacaoComDuracao1() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setTitle("Pauta Sessão 1");
        topicRequest.setDescription("Descrição da pauta para sessão 1");
        ResponseEntity<TopicResponse> topicResponse = topicController.createTopic(topicRequest);
        assertThat(topicResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        TopicResponse topic = topicResponse.getBody();
        assertThat(topic).isNotNull();
        VotingSessionRequest sessionRequest = new VotingSessionRequest();
        sessionRequest.setTopicId(topic.getId());
        sessionRequest.setDuration(1); // 1 minuto
        ResponseEntity<VotingSessionResponse> response = votingSessionController.openSession(sessionRequest);
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        VotingSessionResponse session = response.getBody();
        assertThat(session).isNotNull();
        assertThat(session.getTopicId()).isEqualTo(topic.getId());
        assertThat(session.getId()).isNotNull();
        assertThat(session.getDuration()).isEqualTo(1);
    }

    @Test
    void deveAbrirSessaoDeVotacaoComDuracaoZero() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setTitle("Pauta Sessão Zero");
        topicRequest.setDescription("Descrição da pauta para sessão zero");
        ResponseEntity<TopicResponse> topicResponse = topicController.createTopic(topicRequest);
        assertThat(topicResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        TopicResponse topic = topicResponse.getBody();
        assertThat(topic).isNotNull();
        VotingSessionRequest sessionRequest = new VotingSessionRequest();
        sessionRequest.setTopicId(topic.getId());
        sessionRequest.setDuration(0); // zero
        ResponseEntity<VotingSessionResponse> response = votingSessionController.openSession(sessionRequest);
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        VotingSessionResponse session = response.getBody();
        assertThat(session).isNotNull();
        assertThat(session.getTopicId()).isEqualTo(topic.getId());
        assertThat(session.getId()).isNotNull();
        assertThat(session.getDuration()).isEqualTo(1); // default
    }
    
    @Test
    void deveAbrirSessaoDeVotacaoComDuracaoNull() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setTitle("Pauta Sessão Null");
        topicRequest.setDescription("Descrição da pauta para sessão Null");
        ResponseEntity<TopicResponse> topicResponse = topicController.createTopic(topicRequest);
        assertThat(topicResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        TopicResponse topic = topicResponse.getBody();
        assertThat(topic).isNotNull();
        VotingSessionRequest sessionRequest = new VotingSessionRequest();
        sessionRequest.setTopicId(topic.getId());
        sessionRequest.setDuration(null); // null
        ResponseEntity<VotingSessionResponse> response = votingSessionController.openSession(sessionRequest);
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        VotingSessionResponse session = response.getBody();
        assertThat(session).isNotNull();
        assertThat(session.getTopicId()).isEqualTo(topic.getId());
        assertThat(session.getId()).isNotNull();
        assertThat(session.getDuration()).isEqualTo(1); // default
    }
}
