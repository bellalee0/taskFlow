package com.example.taskflow.domain.auth.service;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.utils.JwtUtil;
import com.example.taskflow.common.utils.PasswordEncoder;
import com.example.taskflow.domain.auth.model.LoginRequest;
import com.example.taskflow.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.example.taskflow.common.exception.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    // 속성
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 기능
    @Transactional
    public String userLogin(LoginRequest request) {
        // 회원가입된 유저의 아이디 조회
        User user = userRepository.findByUserName(request.getUsername()).orElseThrow(
                () -> new CustomException(AUTH_WRONG_EMAIL_AND_PASSWORD)
        );

        // 아이다값이 null이거나 공백 검증
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new CustomException(AUTH_REQUIRED_FIELD);
        }

        // 비밀번호값이 null이거나 공백 검증
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new CustomException(AUTH_REQUIRED_FIELD);
        }

        // 암호환 된 비밀번호 검증
        if (!PasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(AUTH_WRONG_EMAIL_AND_PASSWORD);
        }

        // 토큰 생성 반환
        return jwtUtil.generationToken(request.getUsername());
    }
}
