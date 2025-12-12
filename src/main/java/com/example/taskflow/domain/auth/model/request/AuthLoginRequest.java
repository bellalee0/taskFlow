package com.example.taskflow.domain.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthLoginRequest {
    @NotBlank(message = "username과 password는 필수입니다")
    private String username;
    @NotBlank(message = "username과 password는 필수입니다")
    private String password;
}
