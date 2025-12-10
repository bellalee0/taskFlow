package com.example.taskflow.domain.auth.controller;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.auth.model.LoginRequest;
import com.example.taskflow.domain.auth.model.LoginResponse;
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
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        LoginResponse token = authenticationService.userLogin(request);
        return ResponseEntity.ok(GlobalResponse.success(AUTH_LOGIN_SUCCESS, token));
    }
}
