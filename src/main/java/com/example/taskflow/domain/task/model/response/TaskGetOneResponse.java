package com.example.taskflow.domain.task.model.response;

import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.domain.task.model.dto.TaskDto;
import com.example.taskflow.domain.team.model.response.MemberIdUsernameNameEmailRoleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskGetOneResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final TaskStatus status;
    private final TaskPriority priority;
    private final Long assigneeId;
    private final TaskAssgineeResponse assignee;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime dueDate;

    public static TaskGetOneResponse from(TaskDto taskDto, TaskAssgineeResponse userDto) {
        return new TaskGetOneResponse(
                taskDto.getId(),
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.getStatus(),
                taskDto.getPriority(),
                taskDto.getAssigneeId(),
                userDto,
                taskDto.getCreatedAt(),
                taskDto.getModifiedAt(),
                taskDto.getDueDateTime()
        );
    }
}
