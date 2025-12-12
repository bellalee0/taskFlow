package com.example.taskflow.domain.search.model.dto;

import com.example.taskflow.domain.team.model.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamSearchDto {

    private Long id;
    private String name;
    private String description;

    public static TeamSearchDto from(TeamDto teamDto) {
        return  new TeamSearchDto(
                teamDto.getId(),
                teamDto.getName(),
                teamDto.getDescription()
        );
    }
}

