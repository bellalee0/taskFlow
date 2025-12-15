package com.example.taskflow.domain.search.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.utils.JwtUtil;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import com.example.taskflow.fixture.TaskFixture;
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
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    User user;
    String token;
    Task task;

    @BeforeEach
    void setUp() {
        user = UserFixture.createTestUser();
        userRepository.save(user);

        token = "Bearer " + jwtUtil.generationToken(user.getId(), user.getUsername());

        task = TaskFixture.createTestTask(user);
        taskRepository.save(task);
    }

    @Test
    @DisplayName("GET /api/search 통합 테스트 - 성공: 검색 성공")
    void search_success() throws Exception {

        // Given
        String query = "테스트";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/search")
                .param("query", query)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/search 통합 테스트 - 실패: 검색어가 null인 경우")
    void search_failure_nullQuery() throws Exception {

        // Given
        String query = null;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/search")
                .param("query", query)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/search 통합 테스트 - 실패: 검색어가 공백인 경우")
    void search_failure_blankQuery() throws Exception {

        // Given
        String query = " ";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/search")
                .param("query", query)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isBadRequest());
    }
}