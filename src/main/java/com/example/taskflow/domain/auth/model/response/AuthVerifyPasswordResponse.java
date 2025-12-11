package com.example.taskflow.domain.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthVerifyPasswordResponse {
    private boolean valid;
}
