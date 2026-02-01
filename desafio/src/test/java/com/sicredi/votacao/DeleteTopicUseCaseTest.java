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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class DeleteTopicUseCaseTest {

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
    void deveDeletarTopicComSucesso() {
        // Cria um tópico
        TopicRequest request = new TopicRequest();
        request.setTitle("Pauta Deletar");
        request.setDescription("Descrição para deletar");
        ResponseEntity<TopicResponse> createResponse = topicController.createTopic(request);
        assertThat(createResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        TopicResponse created = createResponse.getBody();
        assertThat(created).isNotNull();
        // Deleta
        ResponseEntity<Void> deleteResponse = topicController.deleteTopic(created.getId());
        assertThat(deleteResponse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.NO_CONTENT);
    }
}
