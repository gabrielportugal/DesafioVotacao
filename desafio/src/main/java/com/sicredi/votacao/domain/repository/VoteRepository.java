package com.sicredi.votacao.domain.repository;

import com.sicredi.votacao.domain.model.Vote;

import java.util.List;
import java.util.Optional;

public interface VoteRepository {

    Vote save(Vote vote);
    Optional<Vote> findByTopicIdAndAssociateId(Long topicId, String associateId);
    List<Vote> findAllByTopicId(Long topicId);
    void deleteAll();

}
