package com.example.taskflow.domain.auth.controller;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.auth.model.request.AuthLoginRequest;
import com.example.taskflow.domain.auth.model.response.AuthLoginResponse;
import com.example.taskflow.domain.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.taskflow.common.model.enums.SuccessMessage.AUTH_LOGIN_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/auth/login")
    public ResponseEntity<GlobalResponse<AuthLoginResponse>> login(@RequestBody AuthLoginRequest request) {
        AuthLoginResponse token = authenticationService.userLogin(request);
        return ResponseEntity.ok(GlobalResponse.success(AUTH_LOGIN_SUCCESS, token));
    }
}
