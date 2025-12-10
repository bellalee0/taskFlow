package com.example.taskflow.domain.task.model.request;

import com.example.taskflow.common.model.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskCreateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;
    private TaskPriority priority;
    private Long assigneeId;
    private LocalDateTime dueDate;
}
