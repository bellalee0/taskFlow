package com.example.taskflow.domain.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.utils.JwtUtil;
import com.example.taskflow.domain.user.repository.UserRepository;
import com.example.taskflow.domain.user.service.UserService;
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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    User user;
    String token;

    @BeforeEach
    void setUp() {
        user = UserFixture.createTestUser();
        userRepository.save(user);

        token = "Bearer " + jwtUtil.generationToken(user.getId(), user.getUsername());
    }

    @Test
    @DisplayName("POST /api/users 통합 테스트 - 성공: 회원가입 성공")
    void createUserApi_success() throws Exception {

        // Given
        String requestBody =
            """
            {
                "username": "test1",
                "email": "test1@test.com",
                "password": "qwer1234!",
                "name": "test1"
            }
            """;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /api/users/{id} 통합 테스트 - 성공: 사용자 정보 조회 성공")
    void getUserApi_success() throws Exception {

        // Given
        long id = user.getId();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/users/%d", id))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/users 통합 테스트 - 성공: 사용자 목록 조회 성공")
    void getUserListApi_success() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /api/users/{id} 통합 테스트 - 성공: 사용자 정보 수정 성공")
    void updateUserInfo_success() throws Exception {

        // Given
        long id = user.getId();

        String requestBody =
            """
            {
                "email": "update@test.com",
                "password": "qwer1234!",
                "name": "update"
            }
            """;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put(String.format("/api/users/%d", id))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/users/{id} 통합 테스트 - 성공: 회원 탈퇴 성공")
    void deleteUserApi_success() throws Exception {

        // Given
        long id = user.getId();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/api/users/%d", id))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/users/available 통합 테스트 - 성공: 추가 가능한 사용자 조회 성공")
    void findTeamUser_success() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/available")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/users/available 통합 테스트 - 성공: 팀별 추가 가능한 사용자 조회 성공")
    void findTeamUser_success_byTeamId() throws Exception {
        // TODO: 팀별 조회 테스트
    }
}