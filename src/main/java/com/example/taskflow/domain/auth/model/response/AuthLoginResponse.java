package com.example.taskflow.domain.auth.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthLoginResponse {
    private final String token;
}
