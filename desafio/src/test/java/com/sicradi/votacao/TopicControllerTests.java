package com.sicradi.votacao;

import com.sicradi.votacao.interfaces.rest.v1.TopicController;
import com.sicradi.votacao.interfaces.rest.dto.TopicRequest;
import com.sicradi.votacao.interfaces.rest.dto.TopicResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TopicControllerTests {

	@Autowired
	private TopicController topicController;

	@Test
	void deveCriarTopicComSucesso() {
		TopicRequest request = new TopicRequest();
		request.setTitle("Pauta Teste");
		request.setDescription("Descrição da pauta de teste");

		ResponseEntity<TopicResponse> response = topicController.createTopic(request);

		assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK); // Se usar ResponseEntity.created, troque para HttpStatus.CREATED
		TopicResponse topic = response.getBody();
		assertThat(topic).isNotNull();
		assertThat(topic.getTitle()).isEqualTo("Pauta Teste");
		assertThat(topic.getDescription()).isEqualTo("Descrição da pauta de teste");
		assertThat(topic.getId()).isNotNull();
	}
}