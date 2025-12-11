package com.example.taskflow.domain.task.model.response;

import com.example.taskflow.domain.task.model.dto.TaskDto;
import com.example.taskflow.domain.team.model.response.MemberIdUsernameNameEmailRoleResponse;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskUpdateStatusResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final String status;
    private final String priority;
    private final Long assigneeId;
    private final TaskAssgineeResponse assignee;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime dueDate;

    public static TaskUpdateStatusResponse from(TaskDto taskDto, TaskAssgineeResponse userDto) {
        return new TaskUpdateStatusResponse(
            taskDto.getId(),
            taskDto.getTitle(),
            taskDto.getDescription(),
            taskDto.getStatus().toString(),
            taskDto.getPriority().toString(),
            taskDto.getAssigneeId(),
            userDto,
            taskDto.getCreatedAt(),
            taskDto.getModifiedAt(),
            taskDto.getDueDateTime()
        );
    }
}
