package com.example.taskflow.domain.task.model.request;

import com.example.taskflow.common.model.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskUpdateStatusRequest {

    private TaskStatus status;
}
