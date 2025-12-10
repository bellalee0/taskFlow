package com.example.taskflow.domain.team.model.response;

import com.example.taskflow.domain.team.model.dto.TeamDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class TeamMemberCreateResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;
    private final List<MemberIdUsernameNameResponse> members;

    public static TeamMemberCreateResponse from(TeamDto dto, List<MemberIdUsernameNameResponse> members) {
        return new TeamMemberCreateResponse(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getCreatedAt(),
                members
        );
    }
}
