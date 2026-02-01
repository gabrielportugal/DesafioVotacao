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
class GetVoteResultUseCaseTest {

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
    void deveRetornarResultadoDaVotacaoComSucesso() {
        // Cria um tópico
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setTitle("Pauta Resultado");
        topicRequest.setDescription("Descrição da pauta para resultado");
        ResponseEntity<TopicResponse> topicResponse = topicController.createTopic(topicRequest);
        assertThat(topicResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        TopicResponse topic = topicResponse.getBody();
        assertThat(topic).isNotNull();
        // Abre sessão
        VotingSessionRequest sessionRequest = new VotingSessionRequest();
        sessionRequest.setTopicId(topic.getId());
        sessionRequest.setDuration(1);
        ResponseEntity<VotingSessionResponse> sessionResponse = votingSessionController.openSession(sessionRequest);
        assertThat(sessionResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        // Registra voto
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setTopicId(topic.getId());
        voteRequest.setAssociateId("98765432100");
        voteRequest.setChoice("Não");
        ResponseEntity<VoteResponse> voteResponse = voteController.registerVote(voteRequest);
        assertThat(voteResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        // Consulta resultado
        ResponseEntity<VoteResultResponse> resultResponse = topicController.getResult(topic.getId());
        assertThat(resultResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        VoteResultResponse result = resultResponse.getBody();
        assertThat(result).isNotNull();
        assertThat(result.getTotalNao()).isGreaterThanOrEqualTo(1);
    }
}
