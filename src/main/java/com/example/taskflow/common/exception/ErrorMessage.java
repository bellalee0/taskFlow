package com.example.taskflow.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    // 400
    BAD_REQUEST_NOT_ALLOWED_SELF_FOLLOW(HttpStatus.BAD_REQUEST, "자기 자신은 팔로우 할 수 없습니다.");




    private final HttpStatus status;
    private final String message;
}


