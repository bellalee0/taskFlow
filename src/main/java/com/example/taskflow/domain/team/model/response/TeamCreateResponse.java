package com.example.taskflow.domain.team.model.response;

import com.example.taskflow.domain.team.model.dto.MemberInfoDto;
import com.example.taskflow.domain.team.model.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class TeamCreateResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;
    private final List<MemberInfoDto> members;

    public static TeamCreateResponse from(TeamDto dto) {
        return new TeamCreateResponse(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getCreatedAt(),
                List.of());
    }
}
