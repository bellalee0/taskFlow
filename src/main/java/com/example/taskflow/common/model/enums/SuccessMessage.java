package com.example.taskflow.common.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor

public enum SuccessMessage {


    // 200
    LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공");


    //201



    //202




    private final HttpStatus status;
    private final String message;
}



