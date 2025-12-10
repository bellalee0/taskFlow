package com.example.taskflow.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    // 400
    AUTH_REQUIRED_FIELD(HttpStatus.BAD_REQUEST, "username과 password는 필수입니다."),
    USER_REQUEST_NOT_VALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "올바른 이메일 형식이 아닙니다."),

    // 401
    AUTH_WRONG_EMAIL_AND_PASSWORD(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."),

    // 403


    // 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    USER_NOT_EMAIL_FOUND(HttpStatus.NOT_FOUND,"이메일을 찾을 수 없습니다."),



    // 409
    USER_USED_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 사용자명입니다."),
    USER_USED_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    USER_REQUEST_NOT_VALID_PASSWORD_FORMAT(HttpStatus.CONFLICT, "비밀번호는 영어와 숫자, 특수문자를 최소 1개 이상 포함해서 4~15자리 이내로 입력해주세요.");


    private final HttpStatus status;
    private final String message;
}


