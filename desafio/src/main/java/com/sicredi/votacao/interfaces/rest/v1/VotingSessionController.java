package com.sicredi.votacao.interfaces.rest.v1;

import com.sicredi.votacao.application.usecase.votingsession.OpenVotingSessionUseCase;
import com.sicredi.votacao.interfaces.rest.dto.VotingSessionRequest;
import com.sicredi.votacao.interfaces.rest.dto.VotingSessionResponse;
import com.sicredi.votacao.interfaces.rest.mapper.VotingSessionMapper;
import com.sicredi.votacao.domain.model.VotingSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.base-path}/voting-session")
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
