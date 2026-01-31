
package com.sicradi.votacao.application.usecase.topic;

import com.sicradi.votacao.domain.repository.VoteRepository;
import com.sicradi.votacao.interfaces.rest.dto.VoteResultResponse;
import com.sicradi.votacao.domain.model.Vote;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GetVoteResultUseCase {

    private final VoteRepository voteRepository;

    public GetVoteResultUseCase(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public VoteResultResponse execute(Long topicId) {
        List<Vote> votes = voteRepository.findAllByTopicId(topicId);
        long totalYes = votes.stream().filter(v -> v.getChoice() == 1).count();
        long totalNo = votes.stream().filter(v -> v.getChoice() == 0).count();
        long total = votes.size();
        double percentualYes = total > 0 ? (totalYes * 100.0) / total : 0.0;
        double percentualNo = total > 0 ? (totalNo * 100.0) / total : 0.0;
        return new VoteResultResponse(totalYes, totalNo, percentualYes, percentualNo);
    }

}
