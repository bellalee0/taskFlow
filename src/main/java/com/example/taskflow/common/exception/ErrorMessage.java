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
    AUTH_WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."),
    TOKEN_REQUIRED_FIELD(HttpStatus.UNAUTHORIZED, "토큰이 필요합니다."),
    TOKEN_INVALID_FIELD(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    // 403


    // 404




    // 409

    ;


    private final HttpStatus status;
    private final String message;
}


