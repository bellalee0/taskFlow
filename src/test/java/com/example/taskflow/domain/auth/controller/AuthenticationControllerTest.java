package com.example.taskflow.domain.auth.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.utils.JwtUtil;
import com.example.taskflow.domain.user.repository.UserRepository;
import com.example.taskflow.fixture.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    User user;
    String token;

    @BeforeEach
    void setUp() {
        user = UserFixture.createTestUser();
        userRepository.save(user);

        token = "Bearer " + jwtUtil.generationToken(user.getId(), user.getUsername());
    }

    @Test
    @DisplayName("POST /api/auth/login 통합 테스트 - 성공: 로그인 성공")
    void loginApi_success() throws Exception {

        // Given
        String requestBody =
            """
            {
                "username" : "test",
                "password" : "qwer1234!"
            }
            """;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/auth/login 통합 테스트 - 실패: 존재하지 않는 유저명")
    void loginApi_failure_wrongUsername() throws Exception {

        // Given
        String requestBody =
            """
            {
                "username" : "test2",
                "password" : "qwer1234!"
            }
            """;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/auth/login 통합 테스트 - 실패: 잘못된 비밀번호")
    void loginApi_failure_wrongPassword() throws Exception {

        // Given
        String requestBody =
            """
            {
                "username" : "test",
                "password" : "1234"
            }
            """;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/users/verify-password 통합 테스트 - 성공: 비밀번호 확인 성공")
    void verifyPasswordApi_success() throws Exception {

        // Given
        String requestBody =
            """
            {
                "password" : "qwer1234!"
            }
            """;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/verify-password")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/users/verify-password 통합 테스트 - 실패: 잘못된 비밀번호")
    void verifyPasswordApi_failure_wrongPassword() throws Exception {

        // Given
        String requestBody =
            """
            {
                "password" : "1234"
            }
            """;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/verify-password")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(requestBody))
            .andExpect(status().isUnauthorized());
    }
}