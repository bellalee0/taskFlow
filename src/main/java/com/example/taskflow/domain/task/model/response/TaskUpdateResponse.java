package com.example.taskflow.domain.task.model.response;

import com.example.taskflow.common.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskUpdateResponse {

    private Long id;
    private String title;
    private String description;
    private String taskStatus;
    private String taskPriority;
    private Long assigneeId;
    private String assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;
    private LocalDateTime timestamp;

    public static TaskUpdateResponse from(Task task) {
        return new TaskUpdateResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getAssigneeId().getId(),
                task.getAssigneeId().getName(),
                task.getCreatedAt(),
                task.getModifiedAt(),
                task.getDueDateTime(),
                LocalDateTime.now()
        );
    }
}
