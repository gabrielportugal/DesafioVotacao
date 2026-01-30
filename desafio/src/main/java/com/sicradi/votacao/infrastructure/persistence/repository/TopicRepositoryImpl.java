package com.sicradi.votacao.infrastructure.persistence.repository;

import com.sicradi.votacao.domain.model.Topic;
import com.sicradi.votacao.domain.repository.TopicRepository;
import com.sicradi.votacao.infrastructure.persistence.entity.TopicEntity;
import com.sicradi.votacao.infrastructure.persistence.mapper.TopicMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

interface SpringDataTopicRepository extends JpaRepository<TopicEntity, Long> {}

@Repository
public class TopicRepositoryImpl implements TopicRepository {
    private final SpringDataTopicRepository springDataTopicRepository;

    public TopicRepositoryImpl(SpringDataTopicRepository springDataTopicRepository) {
        this.springDataTopicRepository = springDataTopicRepository;
    }

    @Override
    public List<Topic> findAll() {
        return springDataTopicRepository.findAll().stream()
                .map(TopicMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Topic> findById(Long id) {
        return springDataTopicRepository.findById(id).map(TopicMapper::toDomain);
    }

    @Override
    public Topic save(Topic topic) {
        TopicEntity entity = TopicMapper.toEntity(topic);
        TopicEntity saved = springDataTopicRepository.save(entity);
        return TopicMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        springDataTopicRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataTopicRepository.existsById(id);
    }
}
