package com.sicradi.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sicradi.votacao.domain.model.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
