
package com.sicradi.votacao.application.usecase.topic;

import org.springframework.stereotype.Service;

import com.sicradi.votacao.domain.repository.VoteRepository;
import com.sicradi.votacao.interfaces.rest.dto.VoteResultResponse;
import com.sicradi.votacao.domain.model.Vote;
import java.util.List;

@Service
public class GetVoteResultUseCase {
    private final VoteRepository voteRepository;

    public GetVoteResultUseCase(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public VoteResultResponse execute(Long topicId) {
        List<Vote> votes = voteRepository.findAllByTopicId(topicId);
        long totalSim = votes.stream().filter(v -> v.getChoice() == 1).count();
        long totalNao = votes.stream().filter(v -> v.getChoice() == 0).count();
        long total = votes.size();
        double percentualSim = total > 0 ? (totalSim * 100.0) / total : 0.0;
        double percentualNao = total > 0 ? (totalNao * 100.0) / total : 0.0;
        return new VoteResultResponse(totalSim, totalNao, percentualSim, percentualNao);
    }
}
