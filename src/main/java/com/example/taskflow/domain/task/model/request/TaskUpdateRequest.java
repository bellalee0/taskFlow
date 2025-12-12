package com.example.taskflow.domain.task.model.request;

import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.common.model.enums.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskUpdateRequest {
    @NotBlank(message = ValidationMessage.TASK_TITLE_ASSIGNEE_NOT_BLANK)
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    @NotNull(message = ValidationMessage.TASK_TITLE_ASSIGNEE_NOT_BLANK)
    private Long assigneeId;
    private LocalDateTime dueDate;
}
