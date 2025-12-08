package com.example.taskflow.common.model.response;

import com.example.taskflow.common.model.enums.SuccessMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.example.taskflow.common.exception.ErrorMessage;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timeStamp;


    //성공시
    public static <T> GlobalResponse<T> success(SuccessMessage successMessage, T data) {
        return new GlobalResponse<>(true, successMessage.getMessage(), data, LocalDateTime.now());
    }

    //예외처리시
    public static GlobalResponse<Void> exception(String errorMessage) {
        return new GlobalResponse<>(false, errorMessage, null, LocalDateTime.now());
    }

    //성공했는데 응답데이터는 없을시
    public static GlobalResponse<Void> successNodata(SuccessMessage successMessage) {
        return new GlobalResponse<>(true, successMessage.getMessage(), null, LocalDateTime.now());
    }
}







