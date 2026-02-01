
package com.sicredi.votacao.application.usecase.topic;

import com.sicredi.votacao.domain.repository.VoteRepository;

import com.sicredi.votacao.interfaces.rest.dto.VoteResultResponse;
import com.sicredi.votacao.domain.model.Vote;
import com.sicredi.votacao.domain.repository.TopicRepository;
import com.sicredi.votacao.domain.model.Topic;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GetVoteResultUseCase {

    private final VoteRepository voteRepository;
    private final TopicRepository topicRepository;

    public GetVoteResultUseCase(VoteRepository voteRepository, TopicRepository topicRepository) {
        this.voteRepository = voteRepository;
        this.topicRepository = topicRepository;
    }

    public VoteResultResponse execute(Long topicId) {
        List<Vote> votes = voteRepository.findAllByTopicId(topicId);
        long totalYes = votes.stream().filter(v -> v.getVot_choice() == 1).count();
        long totalNo = votes.stream().filter(v -> v.getVot_choice() == 0).count();
        long total = votes.size();
        double percentualYes = total > 0 ? (totalYes * 100.0) / total : 0.0;
        double percentualNo = total > 0 ? (totalNo * 100.0) / total : 0.0;
        Topic topic = topicRepository.findById(topicId).orElse(null);
        String title = topic != null ? topic.getTitle() : null;
        String description = topic != null ? topic.getDescription() : null;
        return new VoteResultResponse(totalYes, totalNo, percentualYes, percentualNo, title, description);
    }

}
