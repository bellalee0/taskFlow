package com.example.taskflow.domain.team.model.dto;

import com.example.taskflow.common.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {

    private Long id;
    private String name;
    private String description;

    public static TeamDto from(Team team) {
        return new TeamDto(
            team.getId(),
            team.getName(),
            team.getDescription()
        );
    }
}
