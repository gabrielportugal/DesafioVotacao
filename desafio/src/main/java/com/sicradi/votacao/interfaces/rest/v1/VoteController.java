package com.sicradi.votacao.interfaces.rest.v1;

import com.sicradi.votacao.application.usecase.vote.RegisterVoteUseCase;
import com.sicradi.votacao.interfaces.rest.dto.VoteRequest;
import com.sicradi.votacao.interfaces.rest.dto.VoteResponse;
import com.sicradi.votacao.interfaces.rest.mapper.VoteRestMapper;
import com.sicradi.votacao.domain.model.Vote;
import com.sicradi.votacao.domain.repository.VoteRepository;

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
