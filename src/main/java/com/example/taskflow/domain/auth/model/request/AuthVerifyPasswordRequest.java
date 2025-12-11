package com.example.taskflow.domain.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthVerifyPasswordRequest {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
