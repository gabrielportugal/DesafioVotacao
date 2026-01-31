package com.sicradi.votacao.infrastructure.persistence.repository;

import com.sicradi.votacao.domain.model.Topic;
import com.sicradi.votacao.domain.repository.TopicRepository;
import com.sicradi.votacao.infrastructure.persistence.entity.TopicEntity;
import com.sicradi.votacao.infrastructure.persistence.jpa.TopicRepositoryJpa;
import com.sicradi.votacao.infrastructure.persistence.mapper.TopicMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TopicRepositoryImpl implements TopicRepository {
    
    public TopicRepositoryImpl(TopicRepositoryJpa topicRepositoryJpa) {
        this.topicRepositoryJpa = topicRepositoryJpa;
    }
    private final TopicRepositoryJpa topicRepositoryJpa;

    @Override
    public List<Topic> findAll() {
        return topicRepositoryJpa.findAll().stream()
                .map(TopicMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Topic> findById(Long id) {
        return topicRepositoryJpa.findById(id).map(TopicMapper::toDomain);
    }

    @Override
    public Topic save(Topic topic) {
        TopicEntity entity = TopicMapper.toEntity(topic);
        TopicEntity saved = topicRepositoryJpa.save(entity);
        return TopicMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        topicRepositoryJpa.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return topicRepositoryJpa.existsById(id);
    }

    @Override
    public void deleteAll() {
        topicRepositoryJpa.deleteAll();
    }

}
