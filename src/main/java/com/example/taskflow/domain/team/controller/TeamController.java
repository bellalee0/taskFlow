package com.example.taskflow.domain.team.controller;

import com.example.taskflow.common.entity.Team;
import com.example.taskflow.common.model.enums.SuccessMessage;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.team.model.request.TeamCreateRequest;
import com.example.taskflow.domain.team.model.request.TeamUpdateRequest;
import com.example.taskflow.domain.team.model.response.TeamCreateResponse;
import com.example.taskflow.domain.team.model.response.TeamUpdateResponse;
import com.example.taskflow.domain.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    //팀 생성
    @PostMapping
    public ResponseEntity<GlobalResponse<TeamCreateResponse>> createTeamApi(@RequestBody @Valid TeamCreateRequest request) {
        TeamCreateResponse result = teamService.createTeam(request);
        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.TEAM_CREATE_SUCCESS, result));
    }

    //팀 수정
    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse<TeamUpdateResponse>>  updateTeamApi(@RequestBody @Valid TeamUpdateRequest request, @PathVariable Long id) {
        TeamUpdateResponse result = teamService.updateTeam(request, id);
        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.TEAM_UPDATE_SUCCESS, result));
    }
}
