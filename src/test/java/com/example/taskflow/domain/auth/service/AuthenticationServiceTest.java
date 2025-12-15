package com.example.taskflow.domain.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.utils.JwtUtil;
import com.example.taskflow.common.utils.PasswordEncoder;
import com.example.taskflow.domain.auth.model.request.AuthLoginRequest;
import com.example.taskflow.domain.auth.model.request.AuthVerifyPasswordRequest;
import com.example.taskflow.domain.auth.model.response.AuthLoginResponse;
import com.example.taskflow.domain.auth.model.response.AuthVerifyPasswordResponse;
import com.example.taskflow.domain.user.repository.UserRepository;
import com.example.taskflow.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("로그인 테스트 - 성공: 유효한 유저명과 패스워드 입력")
    void userLogin_success() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        AuthLoginRequest request = new AuthLoginRequest();
        ReflectionTestUtils.setField(request, "username", UserFixture.DEFAULT_USERNAME);
        ReflectionTestUtils.setField(request, "password", UserFixture.DEFAULT_PASSWORD);

        when(userRepository.findLoginUserByUsername(user.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtUtil.generationToken(user.getId(), user.getUsername())).thenReturn("token");

        // When
        AuthLoginResponse response = authenticationService.userLogin(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("token");
    }

    @Test
    @DisplayName("로그인 테스트 - 실패: 잘못된 비밀번호 입력")
    void userLogin_failure_wrongPassword() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        AuthLoginRequest request = new AuthLoginRequest();
        ReflectionTestUtils.setField(request, "username", user.getUsername());
        ReflectionTestUtils.setField(request, "password", "1234");

        when(userRepository.findLoginUserByUsername(user.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(false);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> authenticationService.userLogin(request));
        assertEquals("아이디 또는 비밀번호가 올바르지 않습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("비밀번호 확인 테스트 - 성공: 올바른 패스워드 입력")
    void verifyPassword_success() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        AuthVerifyPasswordRequest request = new AuthVerifyPasswordRequest();
        ReflectionTestUtils.setField(request, "password", UserFixture.DEFAULT_PASSWORD);

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);

        // When
        AuthVerifyPasswordResponse response = authenticationService.verifyPassword(UserFixture.DEFAULT_USERNAME, request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.isValid()).isTrue();
    }

    @Test
    @DisplayName("비밀번호 확인 테스트 - 실패: 잘못된 비밀번호 입력")
    void verifyPassword_failure_wrongPassword() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        AuthVerifyPasswordRequest request = new AuthVerifyPasswordRequest();
        ReflectionTestUtils.setField(request, "password", "1234");

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(false);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> authenticationService.verifyPassword(user.getUsername(), request));
        assertEquals("비밀번호가 올바르지 않습니다.", exception.getMessage());
    }
}