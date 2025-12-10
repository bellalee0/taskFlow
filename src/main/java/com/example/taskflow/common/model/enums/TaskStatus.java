package com.example.taskflow.common.model.enums;

import static com.example.taskflow.common.exception.ErrorMessage.TASK_WRONG_ENUM;

import com.example.taskflow.common.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskStatus {

    TODO(0), IN_PROGRESS(1), DONE(2);

    private int level;

    public static TaskStatus from(String status) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.toString().equals(status)) {
                return taskStatus;
            }
        }
        throw new CustomException(TASK_WRONG_ENUM);
    }
}