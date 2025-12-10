package com.example.taskflow.common.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogType {

    TASK_CREATED("작업 생성"),
    TASK_UPDATED("작업 수정"),
    TASK_DELETED("작업 삭제"),
    TASK_STATUS_CHANGED("작업 상태 변경"),
    COMMENT_CREATED("작업 작성"),
    COMMENT_UPDATED("작업 수정"),
    COMMENT_DELETED("작업 삭제");

    private String action;
}
