package com.example.taskflow.domain.task.model.dto;

import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskDto {

    private final Long id;
    private final String title;
    private final String description;
    private final TaskStatus status;
    private final TaskPriority priority;
    private final Long assigneeId;
    private final LocalDateTime dueDateTime;
    private final boolean isCompleted;
    private final LocalDateTime completedDateTime;
    private final boolean isDeleted;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static TaskDto from(Task task) {
        return new TaskDto(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus(),
            task.getPriority(),
            task.getAssigneeId().getId(),
            task.getDueDate(),
            task.isCompleted(),
            task.getCompletedDateTime(),
            task.isDeleted(),
            task.getCreatedAt(),
            task.getModifiedAt()
        );
    }
}
