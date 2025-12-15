package com.example.taskflow.domain.dashboard.controller;

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
class DashboardControllerTest {

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
    @DisplayName("GET /api/dashboard/stats 통합 테스트 - 성공: 대시보드 통계 성공")
    void getDashboardStatsApi_success() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/dashboard/stats")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/dashboard/tasks 통합 테스트 - 성공: 내 작업 요약 성공")
    void getUserTaskSummaryApi_success() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/dashboard/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/dashboard/weekly-trend 통합 테스트 - 성공: 주간 작업 추세 성공")
    void getWeeklyTrendApi_success() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/dashboard/weekly-trend")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }
}