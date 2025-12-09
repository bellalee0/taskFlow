package com.example.taskflow.domain.task.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.boot.jaxb.internal.stax.LocalSchemaLocator;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskUpdateRequest {

    private String title;
    private String description;
    private String taskStatus;
    private String taskPriority;
    private String assigneeId;
    private LocalDateTime dueDate;
}
