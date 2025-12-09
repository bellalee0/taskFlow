package com.example.taskflow.domain.task.model.response;

import com.example.taskflow.common.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskGetOneResponse {

    private Long id;
    private String title;
    private String description;
    private String TaskStatus;
    private String TaskPriority;
    private String assigneeId;
    private String assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;
    private LocalDateTime timestamp;

    public static TaskGetOneResponse from(Task task) {
        return new TaskGetOneResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getAssigneeId() != null ? task.getAssigneeId().getId().toString() : null,
                task.getAssigneeId() != null ? task.getAssigneeId().getName() : null,
                task.getCreatedAt(),
                task.getModifiedAt(),
                task.getDueDateTime(),
                LocalDateTime.now()
        );
    }
}
