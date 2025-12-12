package com.example.taskflow.domain.auth.model.request;

import com.example.taskflow.common.exception.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthVerifyPasswordRequest {
    @NotBlank(message = ValidationMessage.PASSWORD_REQUIRED)
    private String password;
}
