package com.sicradi.votacao;

import com.sicradi.votacao.interfaces.rest.v1.TopicController;
import com.sicradi.votacao.interfaces.rest.v1.VotingSessionController;
import com.sicradi.votacao.utils.TestDatabaseCleaner;
import com.sicradi.votacao.interfaces.rest.dto.TopicRequest;
import com.sicradi.votacao.interfaces.rest.dto.TopicResponse;
import com.sicradi.votacao.interfaces.rest.dto.VotingSessionRequest;
import com.sicradi.votacao.interfaces.rest.dto.VotingSessionResponse;
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
    void deveAbrirSessaoDeVotacaoComSucesso() {
        // Cria um tópico para associar à sessão
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setTitle("Pauta Sessão");
        topicRequest.setDescription("Descrição da pauta para sessão");
        ResponseEntity<TopicResponse> topicResponse = topicController.createTopic(topicRequest);
        assertThat(topicResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        TopicResponse topic = topicResponse.getBody();
        assertThat(topic).isNotNull();
        // Abre sessão
        VotingSessionRequest sessionRequest = new VotingSessionRequest();
        sessionRequest.setTopicId(topic.getId());
        sessionRequest.setDuration(1); // 1 minuto
        ResponseEntity<VotingSessionResponse> response = votingSessionController.openSession(sessionRequest);
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        VotingSessionResponse session = response.getBody();
        assertThat(session).isNotNull();
        assertThat(session.getTopicId()).isEqualTo(topic.getId());
        assertThat(session.getId()).isNotNull();
    }
}
