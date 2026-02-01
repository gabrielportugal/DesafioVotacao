package com.sicredi.votacao;

import com.sicredi.votacao.interfaces.rest.v1.TopicController;
import com.sicredi.votacao.interfaces.rest.dto.TopicRequest;
import com.sicredi.votacao.interfaces.rest.dto.TopicAndVotingSession;
import com.sicredi.votacao.interfaces.rest.dto.TopicResponse;
import com.sicredi.votacao.interfaces.rest.v1.VotingSessionController;
import com.sicredi.votacao.utils.TestDatabaseCleaner;
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
class GetTopicWithVotingSessionsUseCaseTest {

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
    void deveBuscarTopicComSessoesDeVotacao() {
        // Cria um tópico
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setTitle("Pauta Sessões");
        topicRequest.setDescription("Descrição para sessões");
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
        // Busca topic com sessões
        ResponseEntity<TopicAndVotingSession> response = topicController.getTopicById(topic.getId());
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        TopicAndVotingSession result = response.getBody();
        assertThat(result).isNotNull();
        assertThat(result.getVotingSessions()).isNotEmpty();
    }
}
