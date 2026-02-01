package com.sicredi.votacao.domain.repository;

import com.sicredi.votacao.domain.model.Topic;
import java.util.List;
import java.util.Optional;

public interface TopicRepository {

    List<Topic> findAll();
    Optional<Topic> findById(Long id);
    Topic save(Topic topic);
    void deleteById(Long id);
    boolean existsById(Long id);
    void deleteAll();

}
