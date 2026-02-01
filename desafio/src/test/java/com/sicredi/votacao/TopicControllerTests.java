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
class TopicControllerTests {

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
	void deveCriarTopicComSucesso() {
		TopicRequest request = new TopicRequest();
		request.setTitle("Pauta Teste");
		request.setDescription("Descrição da pauta de teste");
		ResponseEntity<TopicResponse> response = topicController.createTopic(request);
		assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
		TopicResponse topic = response.getBody();
		assertThat(topic).isNotNull();
		assertThat(topic.getTitle()).isEqualTo("Pauta Teste");
		assertThat(topic.getDescription()).isEqualTo("Descrição da pauta de teste");
		assertThat(topic.getId()).isNotNull();
	}
}