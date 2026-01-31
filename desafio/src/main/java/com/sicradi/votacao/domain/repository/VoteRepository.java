package com.sicradi.votacao.domain.repository;

import com.sicradi.votacao.domain.model.Vote;
import java.util.Optional;

public interface VoteRepository {
    Vote save(Vote vote);
    Optional<Vote> findByTopicIdAndAssociateId(Long topicId, String associateId);
}
