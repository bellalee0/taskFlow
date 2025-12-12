package com.example.taskflow.domain.search.model.dto;

import com.example.taskflow.domain.task.model.dto.TaskDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamSearchDto {

    private Long id;
    private String name;
    private String description;

    public static TeamSearchDto from(TaskDto teamDto) {
        return  new TeamSearchDto(
                teamDto.getId(),
                teamDto.getTitle(),
                teamDto.getDescription()
        );
    }
}

