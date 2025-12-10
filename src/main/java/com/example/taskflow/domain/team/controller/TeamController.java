package com.example.taskflow.domain.team.controller;

import com.example.taskflow.common.model.enums.SuccessMessage;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.team.model.request.*;
import com.example.taskflow.domain.team.model.response.*;
import com.example.taskflow.domain.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    //팀 목록 조회
    @GetMapping
    public ResponseEntity<GlobalResponse<List<TeamGetListResponse>>> getTeamListApi(
    ) {
        List<TeamGetListResponse> result = teamService.getTeamList();

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.TEAM_GET_LIST_SUCCESS, result));
    }

    //팀 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<TeamGetOneResponse>> getTeamOneApi(
            @PathVariable Long id
    ) {
        TeamGetOneResponse result = teamService.getTeamOne(id);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.TEAM_GET_ONE_SUCCESS, result));
    }

    //팀 생성
    @PostMapping
    public ResponseEntity<GlobalResponse<TeamCreateResponse>> createTeamApi(
            @RequestBody @Valid TeamCreateRequest request
    ) {
        TeamCreateResponse result = teamService.createTeam(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(GlobalResponse.success(SuccessMessage.TEAM_CREATE_SUCCESS, result));
    }

    //팀 수정
    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse<TeamUpdateResponse>> updateTeamApi(
            @RequestBody @Valid TeamUpdateRequest request,
            @PathVariable Long id
    ) {
        TeamUpdateResponse result = teamService.updateTeam(request, id);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.TEAM_UPDATE_SUCCESS, result));
    }

    //팀 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<Void>> deleteTeamApi(
            @PathVariable Long id
    ) {
        teamService.deleteTeam(id);

        return ResponseEntity.ok(GlobalResponse.successNodata(SuccessMessage.TEAM_DELETE_SUCCESS));
    }

    //팀 멤버 추가
    @PostMapping("/{teamId}/members")
    public ResponseEntity<GlobalResponse<TeamMemberCreateResponse>> createTeamMemberApi(
            @RequestBody @Valid TeamMemberCreateRequest request,
            @PathVariable Long teamId
    ) {
        TeamMemberCreateResponse result = teamService.createTeamMember(request, teamId);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.TEAM_ADD_MEMBER_SUCCESS, result));
    }

    //팀 멤버 제거
    @DeleteMapping("/{teamId}/members/{userId}")
    public ResponseEntity<GlobalResponse<Void>> deleteTeamMemberApi(
            @PathVariable Long teamId,
            @PathVariable Long userId
    ) {
        teamService.deleteTeamMember(teamId, userId);

        return ResponseEntity.ok(GlobalResponse.successNodata(SuccessMessage.TEAM_DELETE_MEMBER_SUCCESS));
    }
}
