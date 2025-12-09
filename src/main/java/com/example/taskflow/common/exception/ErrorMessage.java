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


    // 404
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "팀을 찾을 수 없습니다."),


    // 409
    TEAM_ALREADY_PRESENT(HttpStatus.CONFLICT, "이미 존재하는 팀 이름입니다.")

    ;


    private final HttpStatus status;
    private final String message;
}


