package com.example.taskflow.domain.auth.model.request;

import com.example.taskflow.common.model.enums.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthLoginRequest {
    @NotBlank(message = ValidationMessage.LOGIN_REQUIRED)
    private String username;
    @NotBlank(message = ValidationMessage.LOGIN_REQUIRED)
    private String password;
}
