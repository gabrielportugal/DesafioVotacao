package com.sicradi.votacao.interfaces.rest.controller;

import com.sicradi.votacao.application.usecase.*;
import com.sicradi.votacao.interfaces.rest.dto.*;
import com.sicradi.votacao.domain.model.Topic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/topic")
public class TopicController {
    private final CreateTopicUseCase createTopicUseCase;
    private final GetAllTopicsUseCase getAllTopicsUseCase;
    private final GetTopicByIdUseCase getTopicByIdUseCase;
    private final DeleteTopicUseCase deleteTopicUseCase;

    public TopicController(CreateTopicUseCase createTopicUseCase,
                          GetAllTopicsUseCase getAllTopicsUseCase,
                          GetTopicByIdUseCase getTopicByIdUseCase,
                          DeleteTopicUseCase deleteTopicUseCase) {
        this.createTopicUseCase = createTopicUseCase;
        this.getAllTopicsUseCase = getAllTopicsUseCase;
        this.getTopicByIdUseCase = getTopicByIdUseCase;
        this.deleteTopicUseCase = deleteTopicUseCase;
    }

    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@RequestBody TopicRequest request) {
        Topic topic = createTopicUseCase.execute(request.getTitle(), request.getDescription());
        TopicResponse response = toResponse(topic);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics() {
        List<Topic> topics = getAllTopicsUseCase.execute();
        List<TopicResponse> responses = topics.stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable Long id) {
        Topic topic = getTopicByIdUseCase.execute(id);
        return ResponseEntity.ok(toResponse(topic));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        deleteTopicUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    private TopicResponse toResponse(Topic topic) {
        TopicResponse response = new TopicResponse();
        response.setId(topic.getId());
        response.setTitle(topic.getTitle());
        response.setDescription(topic.getDescription());
        response.setStatus(topic.getStatus());
        response.setCreatedAt(topic.getCreatedAt());
        response.setUpdatedAt(topic.getUpdatedAt());
        return response;
    }
}
