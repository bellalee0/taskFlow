package com.example.taskflow.domain.task.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskCreateRequest {

    private String title;
    private String description;
    private String priority;
    private String assigneeId;
    private String dueDate;
}
