package com.sicredi.votacao;

import com.sicredi.votacao.interfaces.rest.v1.TopicController;
import com.sicredi.votacao.utils.TestDatabaseCleaner;
import com.sicredi.votacao.interfaces.rest.dto.TopicRequest;
import com.sicredi.votacao.interfaces.rest.dto.TopicAndVotingSession;
import com.sicredi.votacao.interfaces.rest.dto.TopicResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.AfterEach;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class GetTopicByIdUseCaseTest {

    @Autowired
    private TopicController topicController;

    @Autowired
    private TestDatabaseCleaner testDatabaseCleaner;

    @AfterEach
    void cleanDatabaseAfterEach() {
        testDatabaseCleaner.cleanDatabase();
        System.out.println("\uD83E\uDEB9 Banco de dados limpo após o teste");
    }

    @Test
    void deveBuscarTopicPorIdComSucesso() {
        // Cria um tópico para garantir que existe
        TopicRequest request = new TopicRequest();
        request.setTitle("Pauta Busca");
        request.setDescription("Descrição para busca por ID");
        ResponseEntity<TopicResponse> createResponse = topicController.createTopic(request);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        TopicResponse created = createResponse.getBody();
        assertThat(created).isNotNull();
        // Busca pelo ID
        ResponseEntity<TopicAndVotingSession> response = topicController.getTopicById(created.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TopicAndVotingSession topic = response.getBody();
        assertThat(topic).isNotNull();
        assertThat(topic.getTopicId()).isEqualTo(created.getId());
        assertThat(topic.getTitle()).isEqualTo("Pauta Busca");
    }

}
