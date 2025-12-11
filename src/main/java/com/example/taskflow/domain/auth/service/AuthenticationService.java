package com.example.taskflow.domain.auth.service;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.utils.JwtUtil;
import com.example.taskflow.common.utils.PasswordEncoder;
import com.example.taskflow.domain.auth.model.request.*;
import com.example.taskflow.domain.auth.model.response.*;
import com.example.taskflow.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.example.taskflow.common.exception.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // 로그인
    @Transactional
    public AuthLoginResponse userLogin(AuthLoginRequest request) {

        User user = userRepository.findUserByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(AUTH_WRONG_EMAIL_AND_PASSWORD);
        }

        return new AuthLoginResponse(jwtUtil.generationToken(user.getId(), user.getUsername()));
    }

    // 비밀번호 확인
    @Transactional
    public AuthVerifyPasswordResponse verifyPassword(String username, @Valid AuthVerifyPasswordRequest request) {

        User user = userRepository.findUserByUsername(username);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(AUTH_WRONG_PASSWORD);
        }

        return new AuthVerifyPasswordResponse(true);
    }
}
