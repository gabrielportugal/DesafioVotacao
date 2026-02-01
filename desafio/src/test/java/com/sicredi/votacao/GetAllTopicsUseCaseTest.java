package com.sicredi.votacao;

import com.sicredi.votacao.interfaces.rest.v1.TopicController;
import com.sicredi.votacao.utils.TestDatabaseCleaner;
import com.sicredi.votacao.interfaces.rest.dto.TopicRequest;
import com.sicredi.votacao.interfaces.rest.dto.TopicResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.AfterEach;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class GetAllTopicsUseCaseTest {

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
    void deveBuscarTodosOsTopicsComSucesso() {
        // Cria um tópico para garantir que existe pelo menos um
        TopicRequest request = new TopicRequest();
        request.setTitle("Pauta Listagem");
        request.setDescription("Descrição para listagem");
        ResponseEntity<TopicResponse> createResponse = topicController.createTopic(request);
        // Busca todos
        ResponseEntity<List<TopicResponse>> response = topicController.getAllTopics();
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        List<TopicResponse> topics = response.getBody();
        assertThat(topics).isNotNull();
        assertThat(topics.size()).isGreaterThanOrEqualTo(1);
    }
}
