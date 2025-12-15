package com.example.taskflow.domain.task.model.request;

import com.example.taskflow.common.model.enums.TaskStatus;
import lombok.Getter;

@Getter
public class TaskUpdateStatusRequest {
    private TaskStatus status;
}
