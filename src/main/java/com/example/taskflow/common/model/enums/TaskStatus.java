package com.example.taskflow.common.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskStatus {

    TODO(0), IN_PROGRESS(1), DONE(2);

    private int level;
}