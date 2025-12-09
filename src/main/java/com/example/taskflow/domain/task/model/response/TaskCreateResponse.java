package com.example.taskflow.domain.task.model.response;

import com.example.taskflow.common.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskCreateResponse {

    private Long id;
    private String title;
    private String description;
    private String TaskStatus;
    private String TaskPriority;
    private String assigneeId;
    private String assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDateTime;
    private LocalDateTime timestamp;

    public static TaskCreateResponse from(Task task) {
        return new TaskCreateResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus() != null ? task.getStatus().name() : null,
                task.getPriority() != null ? task.getPriority().name() : null,
                task.getAssigneeId() != null ? task.getAssigneeId().getId().toString() : null,
                task.getAssigneeId() != null ? task.getAssigneeId().getName() : null,
                task.getCreatedAt(),
                task.getModifiedAt(),
                task.getDueDateTime(),
                LocalDateTime.now()
        );
    }
}