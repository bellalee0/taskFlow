package com.example.taskflow.domain.team.model.dto;

import com.example.taskflow.common.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TeamDto {

    private final Long id;
    private final String name;
    private final String description;
    private final boolean isDeleted;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static TeamDto from(Team team) {
        return new TeamDto(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.isDeleted(),
                team.getCreatedAt(),
                team.getModifiedAt()
        );
    }
}
