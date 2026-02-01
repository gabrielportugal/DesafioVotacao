package com.sicredi.votacao.interfaces.rest.v1;

import com.sicredi.votacao.application.usecase.vote.RegisterVoteUseCase;
import com.sicredi.votacao.interfaces.rest.dto.VoteRequest;
import com.sicredi.votacao.interfaces.rest.dto.VoteResponse;
import com.sicredi.votacao.interfaces.rest.mapper.VoteRestMapper;
import com.sicredi.votacao.domain.model.Vote;
import com.sicredi.votacao.domain.repository.VoteRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.base-path}/votes")
public class VoteController {

    private final RegisterVoteUseCase registerVoteUseCase;
    private final VoteRepository voteRepository;

    public VoteController(RegisterVoteUseCase registerVoteUseCase, VoteRepository voteRepository) {
        this.registerVoteUseCase = registerVoteUseCase;
        this.voteRepository = voteRepository;
    }

    @PostMapping
    public ResponseEntity<VoteResponse> registerVote(@RequestBody VoteRequest request) {
        Vote vote = registerVoteUseCase.execute(request.getTopicId(), request.getAssociateId(), request.getChoice());
        VoteResponse response = VoteRestMapper.toResponse(vote);
        return ResponseEntity.ok(response);
    }

}
