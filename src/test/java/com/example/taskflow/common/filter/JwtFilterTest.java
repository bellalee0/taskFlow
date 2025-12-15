package com.example.taskflow.common.filter;

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
class JwtFilterTest {

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
    @DisplayName("POST /api/users/verify-password 통합 테스트 - 실패: 토큰 X")
    void jwtFilter_failure_unAuthorized() throws Exception {

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
                .content(requestBody))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/users/verify-password 통합 테스트 - 실패: Bearer로 시작하지 않는 토큰일 때")
    void jwtFilter_failure_notValidToken() throws Exception {

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
                .header("Authorization", "notValidToken")
                .content(requestBody))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/users/verify-password 통합 테스트 - 실패: Bearer로 시작하지만, 유효하지 않은 토큰일 때")
    void jwtFilter_failure_notValidBearerToken() throws Exception {

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
                .header("Authorization", "Bearer notValidBearerToken")
                .content(requestBody))
            .andExpect(status().isUnauthorized());
    }
}