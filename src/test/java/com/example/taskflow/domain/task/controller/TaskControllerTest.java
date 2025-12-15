package com.example.taskflow.domain.task.controller;

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
class TaskControllerTest {

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
    @DisplayName("POST /api/tasks 통합 테스트 - 성공: 작업 생성 성공")
    void createTaskApi() throws Exception {

        // Given
        String requestBody = String.format(
            """
            {
                "title": "test",
                "description": "test",
                "priority": "LOW",
                "assigneeId": %d,
                "dueDate": "2025-12-15T10:13:07.211267"
            }
            """, user.getId());

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(requestBody))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /api/tasks 통합 테스트 - 성공: 작업 목록 조회 성공")
    void getTaskListApi() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/tasks/{id} 통합 테스트 - 성공: 작업 상세 조회 성공")
    void getTaskApi() throws Exception {

        // Given
        long id = task.getId();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/tasks/%d", id))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /api/tasks/{id} 통합 테스트 - 성공: 작업 수정 성공")
    void updateTaskApi() throws Exception {

        // Given
        long id = task.getId();

        String requestBody = String.format(
            """
            {
                "title": "제목 수정",
                "description": "설명 수정",
                "status": "IN_PROGRESS",
                "priority": "HIGH",
                "assigneeId": %d,
                "dueDate": "2025-12-17T10:13:07.211267"
            }
            """, user.getId());

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put(String.format("/api/tasks/%d", id))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/tasks/{id}/status 통합 테스트 - 성공: 작업 상태 변경 성공")
    void updateStatusApi() throws Exception {

        // Given
        long id = task.getId();

        String requestBody =
            """
            {
                "status": "IN_PROGRESS"
            }
            """;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.patch(String.format("/api/tasks/%d/status", id))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/tasks/{id} 통합 테스트 - 성공: 작업 삭제 성공")
    void deleteTaskApi() throws Exception {

        // Given
        long id = task.getId();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/api/tasks/%d", id))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }
}