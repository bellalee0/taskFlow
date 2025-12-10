package com.example.taskflow.domain.team.model.response;

import com.example.taskflow.domain.team.model.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TeamIdNameDesCrAtResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;

    public static TeamIdNameDesCrAtResponse from(TeamDto dto){
        return new TeamIdNameDesCrAtResponse(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getCreatedAt()
        );
    }
}
