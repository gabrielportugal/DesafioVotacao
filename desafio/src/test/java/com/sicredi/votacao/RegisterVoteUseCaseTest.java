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
class RegisterVoteUseCaseTest {

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
    void deveRegistrarVotoComCpfValido() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setTitle("Pauta Voto");
        topicRequest.setDescription("Descrição da pauta para voto");
        ResponseEntity<TopicResponse> topicResponse = topicController.createTopic(topicRequest);
        assertThat(topicResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        TopicResponse topic = topicResponse.getBody();
        assertThat(topic).isNotNull();
        VotingSessionRequest sessionRequest = new VotingSessionRequest();
        sessionRequest.setTopicId(topic.getId());
        sessionRequest.setDuration(1);
        ResponseEntity<VotingSessionResponse> sessionResponse = votingSessionController.openSession(sessionRequest);
        assertThat(sessionResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        VotingSessionResponse session = sessionResponse.getBody();
        assertThat(session).isNotNull();
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setTopicId(topic.getId());
        voteRequest.setAssociateId("52998224725"); // CPF válido
        voteRequest.setChoice("Sim");
        ResponseEntity<VoteResponse> voteResponse = voteController.registerVote(voteRequest);
        assertThat(voteResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        VoteResponse vote = voteResponse.getBody();
        assertThat(vote).isNotNull();
        assertThat(vote.getTopicId()).isEqualTo(topic.getId());
        assertThat(vote.getAssociateId()).isEqualTo("52998224725");
        assertThat(vote.getChoice()).isEqualTo(1);
        assertThat(vote.getId()).isNotNull();
    }

    @Test
    void deveFalharComCpfInvalido() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setTitle("Pauta Voto");
        topicRequest.setDescription("Descrição da pauta para voto");
        ResponseEntity<TopicResponse> topicResponse = topicController.createTopic(topicRequest);
        assertThat(topicResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        TopicResponse topic = topicResponse.getBody();
        assertThat(topic).isNotNull();
        VotingSessionRequest sessionRequest = new VotingSessionRequest();
        sessionRequest.setTopicId(topic.getId());
        sessionRequest.setDuration(1);
        ResponseEntity<VotingSessionResponse> sessionResponse = votingSessionController.openSession(sessionRequest);
        assertThat(sessionResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        VotingSessionResponse session = sessionResponse.getBody();
        assertThat(session).isNotNull();
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setTopicId(topic.getId());
        voteRequest.setAssociateId("12345678900"); // CPF inválido
        voteRequest.setChoice("Sim");
        try {
            ResponseEntity<VoteResponse> voteResponse = voteController.registerVote(voteRequest);
            assertThat(voteResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            assertThat(ex.getMessage()).contains("CPF inválido");
        }
    }
}
