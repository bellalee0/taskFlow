package com.example.taskflow.domain.team.model.response;

import com.example.taskflow.domain.team.model.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class TeamGetOneResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;
    private final List<MemberIdUsernameNameEmailRoleResponse> members;

    public static TeamGetOneResponse from(TeamDto dto, List<MemberIdUsernameNameEmailRoleResponse> members) {
        return new TeamGetOneResponse(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getCreatedAt(),
                members
        );
    }
}
