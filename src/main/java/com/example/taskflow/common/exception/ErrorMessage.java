package com.example.taskflow.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    // 400
    AUTH_REQUIRED_FIELD(HttpStatus.BAD_REQUEST, "username과 password는 필수입니다."),

    // 401
    AUTH_WRONG_EMAIL_AND_PASSWORD(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."),

    // 403
    COMMENT_NO_PERMISSION(HttpStatus.FORBIDDEN, "댓글을 수정할 권한이 없습니다."),

    // 404
    COMMENT_NOT_FOUND_TASK_OR_COMMENT(HttpStatus.NOT_FOUND, "작업을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),

    // 409

    ;


    private final HttpStatus status;
    private final String message;
}


