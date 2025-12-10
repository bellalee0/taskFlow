package com.example.taskflow.domain.team.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TeamGetListResponse {
    private final List<TeamIdNameDesCrAtResponse> teamList;

    public static TeamGetListResponse from(List<TeamIdNameDesCrAtResponse> teamList){
        return new TeamGetListResponse(teamList);
    }
}