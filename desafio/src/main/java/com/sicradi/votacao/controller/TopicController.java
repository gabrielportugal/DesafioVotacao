package com.sicradi.votacao.controller;

import com.sicradi.votacao.service.TopicService;
import com.sicradi.votacao.domain.model.Topic;
import com.sicradi.votacao.exceptions.ResourceNotFoundException;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/topic")
public class TopicController {
  private final TopicService topicService;

  public TopicController(TopicService topicService) {
    this.topicService = topicService;
  }

  @PostMapping
  public Topic createTopic(@RequestBody Topic topic) {
    return topicService.createTopic(topic);
  }

  @GetMapping()
  public List<Topic> getAllTopics() {
    return topicService.getAllTopics();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getTopicById(@PathVariable Long id) {
    Topic topic = topicService.getTopicById(id);
    return ResponseEntity.ok(topic);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
    topicService.deleteTopic(id);
    return ResponseEntity.noContent().build();
  }
}
