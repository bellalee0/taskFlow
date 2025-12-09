package com.example.taskflow.domain.task.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskCreateRequest {

    private String title;
    private String description;
    private String priority;
    private String assigneeId;
    private String dueDateTime;
}
