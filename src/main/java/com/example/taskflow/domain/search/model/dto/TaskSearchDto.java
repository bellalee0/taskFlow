package com.example.taskflow.domain.search.model.dto;

import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.domain.task.model.dto.TaskDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskSearchDto {

    private final Long id;
    private final String title;
    private final String description;
    private final TaskStatus status;

    public static TaskSearchDto from(TaskDto taskDto) {
        return new TaskSearchDto(
                taskDto.getId(),
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.getStatus()
                );
    }
}
