package com.example.taskflow.domain.auth.model;

public class LoginRequest {
    // 속성
    // 로그인 요청 도메인
    private String username;
    private String password;

    // 기능
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
