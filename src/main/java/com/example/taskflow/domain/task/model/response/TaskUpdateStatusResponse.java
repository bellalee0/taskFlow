package com.example.taskflow.domain.task.model.response;

import com.example.taskflow.domain.task.model.dto.TaskDto;
import com.example.taskflow.domain.user.model.dto.UserDto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskUpdateStatusResponse {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private Long assigneeId;
    private UserDto assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;

    public static TaskUpdateStatusResponse from(TaskDto taskDto, UserDto userDto) {
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
