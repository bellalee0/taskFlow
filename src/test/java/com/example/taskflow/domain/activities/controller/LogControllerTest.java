package com.example.taskflow.domain.activities.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.entity.Log;
import com.example.taskflow.common.model.enums.LogType;
import com.example.taskflow.common.utils.JwtUtil;
import com.example.taskflow.domain.activities.repository.LogRepository;
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
class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private LogRepository logRepository;

    User user;
    String token;
    Task task;
    Log log;

    @BeforeEach
    void setUp() {
        user = UserFixture.createTestUser();
        userRepository.save(user);

        token = "Bearer " + jwtUtil.generationToken(user.getId(), user.getUsername());

        task = TaskFixture.createTestTask(user);
        taskRepository.save(task);

        log = new Log(LogType.TASK_CREATED, user, task, LogType.TASK_CREATED.getDescription());
        logRepository.save(log);
    }

    @Test
    @DisplayName("GET api/activities 통합 테스트 - 성공: 전체 활동 로그 조회 성공")
    void getAllLogsApi() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET api/activities/me 통합 테스트 - 성공: 전체 활동 로그 조회 성공")
    void getMyLogsApi() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/activities/me")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }
}