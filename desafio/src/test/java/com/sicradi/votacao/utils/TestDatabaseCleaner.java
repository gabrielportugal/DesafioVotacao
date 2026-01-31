package com.sicradi.votacao.utils;

import com.sicradi.votacao.domain.repository.TopicRepository;
import com.sicradi.votacao.domain.repository.VoteRepository;
import com.sicradi.votacao.domain.repository.VotingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDatabaseCleaner {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private VotingSessionRepository votingSessionRepository;
    @Autowired
    private TopicRepository topicRepository;

    public void cleanDatabase() {
        voteRepository.deleteAll();
        votingSessionRepository.deleteAll();
        topicRepository.deleteAll();
    }
}
