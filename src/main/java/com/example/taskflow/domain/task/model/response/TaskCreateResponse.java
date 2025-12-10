package com.example.taskflow.domain.task.model.response;

import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.domain.task.model.dto.TaskDto;
import com.example.taskflow.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskCreateResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Long assigneeId;
    private UserDto assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;

    public static TaskCreateResponse from(TaskDto taskDto, UserDto userDto) {
        return new TaskCreateResponse(
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