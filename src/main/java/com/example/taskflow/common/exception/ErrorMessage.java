package com.example.taskflow.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    // 400
    AUTH_REQUIRED_FIELD(HttpStatus.BAD_REQUEST, "username과 password는 필수입니다."),
    TASK_REQUIRED_FIELD(HttpStatus.BAD_REQUEST, "제목과 담당자는 필수입니다."),
    TASK_BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청 파라미터입니다."),
    TASK_invalid(HttpStatus.BAD_REQUEST, "유효하지 않은 상태 값입니다."),

    // 401
    AUTH_WRONG_EMAIL_AND_PASSWORD(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."),

    // 403
    TASK_UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN, "수정 권한이 없습니다."),
    TASK_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN, "작업을 삭제할 권한이 없습니다."),


    // 404
    ASSIGNEE_NOT_FOUND(HttpStatus.NOT_FOUND, "담당자를 찾을 수 없습니다."),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "작업을 찾을 수 없습니다.")

    // 409

    ;


    private final HttpStatus status;
    private final String message;
}


