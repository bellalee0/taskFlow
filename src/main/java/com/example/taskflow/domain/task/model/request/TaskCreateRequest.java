package com.example.taskflow.domain.task.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskCreateRequest {

    private String title;
    private String description;
    private String taskPriority;
    private String assigneeId;
    private String dueDate;
}
