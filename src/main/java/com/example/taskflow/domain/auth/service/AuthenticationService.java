package com.example.taskflow.domain.auth.service;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.utils.JwtUtil;
import com.example.taskflow.common.utils.PasswordEncoder;
import com.example.taskflow.domain.auth.model.LoginRequest;
import com.example.taskflow.domain.auth.model.LoginResponse;
import com.example.taskflow.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.example.taskflow.common.exception.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public LoginResponse userLogin(LoginRequest request) {

        User user = userRepository.findByUserName(request.getUsername()).orElseThrow(
                () -> new CustomException(AUTH_WRONG_EMAIL_AND_PASSWORD)
        );

        if (!PasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(AUTH_WRONG_EMAIL_AND_PASSWORD);
        }

        return new LoginResponse(jwtUtil.generationToken(user.getUserName()));
    }
}
