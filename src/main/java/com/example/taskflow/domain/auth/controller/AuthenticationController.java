package com.example.taskflow.domain.auth.controller;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.auth.model.LoginRequest;
import com.example.taskflow.domain.auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.taskflow.common.model.enums.SuccessMessage.AUTH_LOGIN_SUCCESS;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
    // 속성
    private final AuthenticationService authenticationService;

    // 생성자
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // 기능
    // 로그인 요청
    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<String>> login(@RequestBody LoginRequest request) {
        // 인증 서비스의 로그인기능
        String bearerToken = authenticationService.userLogin(request);
        return ResponseEntity.ok(GlobalResponse.success(AUTH_LOGIN_SUCCESS, bearerToken));
    }
}
