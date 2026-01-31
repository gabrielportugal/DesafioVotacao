package com.sicradi.votacao.interfaces.rest.controller;

import com.sicradi.votacao.application.usecase.vote.RegisterVoteUseCase;
import com.sicradi.votacao.interfaces.rest.dto.VoteRequest;
import com.sicradi.votacao.interfaces.rest.dto.VoteResponse;
import com.sicradi.votacao.interfaces.rest.mapper.VoteRestMapper;
import com.sicradi.votacao.domain.model.Vote;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votes")
public class VoteController {
    private final RegisterVoteUseCase registerVoteUseCase;

    public VoteController(RegisterVoteUseCase registerVoteUseCase) {
        this.registerVoteUseCase = registerVoteUseCase;
    }

    @PostMapping
    public ResponseEntity<VoteResponse> registerVote(@RequestBody VoteRequest request) {
        Vote vote = registerVoteUseCase.execute(request.getTopicId(), request.getAssociateId(), request.getChoice());
        VoteResponse response = VoteRestMapper.toResponse(vote);
        return ResponseEntity.ok(response);
    }
}
