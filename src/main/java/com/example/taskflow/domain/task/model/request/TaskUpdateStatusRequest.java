package com.example.taskflow.domain.task.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskUpdateStatusRequest {

    // TODO: 프론트에서 어떤 자료형으로 보내주는지 확인 필요
    private String status;
}
