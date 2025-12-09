package com.example.taskflow.common.model.response;

import com.example.taskflow.common.model.enums.SuccessMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
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


    // 성공 시 (사용 예시: GlobalResponse.success(SuccessMessage 이넘, 반환 DTO)
    public static <T> GlobalResponse<T> success(SuccessMessage successMessage, T data) {
        return new GlobalResponse<>(true, successMessage.getMessage(), data, LocalDateTime.now());
    }

    // 성공했는데 응답 데이터는 없을 시 (사용 예시: GlobalResponse.successNodata(SuccessMessage 이넘)
    public static GlobalResponse<Void> successNodata(SuccessMessage successMessage) {
        return new GlobalResponse<>(true, successMessage.getMessage(), null, LocalDateTime.now());
    }

    // 예외 처리 시
    public static GlobalResponse<Void> exception(String errorMessage) {
        return new GlobalResponse<>(false, errorMessage, null, LocalDateTime.now());
    }
}







