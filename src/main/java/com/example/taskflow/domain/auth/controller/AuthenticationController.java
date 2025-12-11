package com.example.taskflow.domain.auth.controller;

import com.example.taskflow.common.model.enums.SuccessMessage;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.auth.model.request.*;
import com.example.taskflow.domain.auth.model.response.*;
import com.example.taskflow.domain.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<GlobalResponse<AuthLoginResponse>> loginApi(
        @Valid @RequestBody AuthLoginRequest request
    ) {
        AuthLoginResponse token = authenticationService.userLogin(request);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.AUTH_LOGIN_SUCCESS, token));
    }

    // 비밀번호 확인
//    @PostMapping("/users/verify-password")
    @PostMapping("/users/verify-password/{userId}")
    public ResponseEntity<GlobalResponse<AuthVerifyPasswordResponse>> verifyPasswordApi(
        // TODO: 로그인된 사용자 정보로 변경
        @PathVariable long userId,
        @Valid @RequestBody AuthVerifyPasswordRequest request
    ) {
        AuthVerifyPasswordResponse result = authenticationService.verifyPassword(userId, request);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.AUTH_CHECK_PASSWORD_SUCCESS, result));
    }
}
