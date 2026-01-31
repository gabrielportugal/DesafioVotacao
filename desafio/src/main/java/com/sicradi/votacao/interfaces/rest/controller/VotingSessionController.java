package com.sicradi.votacao.interfaces.rest.controller;

import com.sicradi.votacao.application.usecase.votingsession.OpenVotingSessionUseCase;
import com.sicradi.votacao.interfaces.rest.dto.VotingSessionRequest;
import com.sicradi.votacao.interfaces.rest.dto.VotingSessionResponse;
import com.sicradi.votacao.interfaces.rest.mapper.VotingSessionMapper;
import com.sicradi.votacao.domain.model.VotingSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voting-session")
public class VotingSessionController {
    private final OpenVotingSessionUseCase openVotingSessionUseCase;

    public VotingSessionController(OpenVotingSessionUseCase openVotingSessionUseCase) {
        this.openVotingSessionUseCase = openVotingSessionUseCase;
    }

    @PostMapping
    public ResponseEntity<VotingSessionResponse> openSession(@RequestBody VotingSessionRequest request) {
        VotingSession session = openVotingSessionUseCase.execute(request.getTopicId(), request.getDuration());
        VotingSessionResponse response = VotingSessionMapper.toResponse(session);
        return ResponseEntity.ok(response);
    }
}
