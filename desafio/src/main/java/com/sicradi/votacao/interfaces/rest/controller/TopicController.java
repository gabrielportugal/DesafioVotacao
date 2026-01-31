package com.sicradi.votacao.interfaces.rest.controller;

import com.sicradi.votacao.application.usecase.topic.CreateTopicUseCase;
import com.sicradi.votacao.application.usecase.topic.DeleteTopicUseCase;
import com.sicradi.votacao.application.usecase.topic.GetAllTopicsUseCase;
import com.sicradi.votacao.application.usecase.topic.GetTopicByIdUseCase;
import com.sicradi.votacao.application.usecase.topic.GetVoteResultUseCase;
import com.sicradi.votacao.interfaces.rest.dto.*;
import com.sicradi.votacao.interfaces.rest.mapper.TopicMapper;
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
    private final GetVoteResultUseCase getVoteResultUseCase;

    public TopicController(CreateTopicUseCase createTopicUseCase,
                          GetAllTopicsUseCase getAllTopicsUseCase,
                          GetTopicByIdUseCase getTopicByIdUseCase,
                          DeleteTopicUseCase deleteTopicUseCase,
                          GetVoteResultUseCase getVoteResultUseCase) {
        this.createTopicUseCase = createTopicUseCase;
        this.getAllTopicsUseCase = getAllTopicsUseCase;
        this.getTopicByIdUseCase = getTopicByIdUseCase;
        this.deleteTopicUseCase = deleteTopicUseCase;
        this.getVoteResultUseCase = getVoteResultUseCase;
    }

    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@RequestBody TopicRequest request) {
        Topic topic = createTopicUseCase.execute(request.getTitle(), request.getDescription());
        TopicResponse response = TopicMapper.toResponse(topic);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics() {
        List<Topic> topics = getAllTopicsUseCase.execute();
        List<TopicResponse> responses = topics.stream().map(TopicMapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable Long id) {
        Topic topic = getTopicByIdUseCase.execute(id);
        return ResponseEntity.ok(TopicMapper.toResponse(topic));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        deleteTopicUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/result/{topicId}")
    public ResponseEntity<VoteResultResponse> getResult(@PathVariable Long topicId) {
        var response = getVoteResultUseCase.execute(topicId);
        return ResponseEntity.ok(response);
    }
}
