package com.example.taskflow.domain.comment.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taskflow.common.entity.Comment;
import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.utils.JwtUtil;
import com.example.taskflow.domain.comment.repository.CommentRepository;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import com.example.taskflow.fixture.CommentFixture;
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
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    User user;
    String token;
    Task task;
    Comment comment;

    @BeforeEach
    void setUp() {
        user = UserFixture.createTestUser();
        userRepository.save(user);

        token = "Bearer " + jwtUtil.generationToken(user.getId(), user.getUsername());

        task = TaskFixture.createTestTask(user);
        taskRepository.save(task);

        comment = CommentFixture.createTestParentComment(user, task);
        commentRepository.save(comment);
    }

    @Test
    @DisplayName("POST /api/tasks/{taskId}/comments 통합 테스트 - 성공: 댓글 생성 성공")
    void createCommentApi_success() throws Exception {

        // Given
        long taskId = task.getId();

        String requestBody =
            """
            {
                "content": "댓글",
                "parentId": null
            }
            """;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post(String.format("/api/tasks/%d/comments", taskId))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(requestBody))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /api/tasks/{taskId}/comments 통합 테스트 - 성공: 댓글 목록 조회(최신순) 성공")
    void getCommentListApi_success_sortNewest() throws Exception {

        // Given
        long taskId = task.getId();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/tasks/%d/comments", taskId))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/tasks/{taskId}/comments 통합 테스트 - 성공: 댓글 목록 조회(오래된 순) 성공")
    void getCommentListApi_success_sortOldest() throws Exception {

        // Given
        long taskId = task.getId();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/tasks/%d/comments", taskId))
                .param("sort", "oldest")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /api/tasks/{taskId}/comments/{commentId} 통합 테스트 - 성공: 댓글 수정 성공")
    void updateCommentApi_success() throws Exception {

        // Given
        long taskId = task.getId();
        long commentId = comment.getId();

        String requestBody =
            """
            {
                "content": "댓글 수정"
            }
            """;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put(String.format("/api/tasks/%d/comments/%d", taskId, commentId))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/tasks/{taskId}/comments/{commentId} 통합 테스트 - 성공: 댓글 삭제 성공")
    void deleteCommentApi_success() throws Exception {

        // Given
        long taskId = task.getId();
        long commentId = comment.getId();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/api/tasks/%d/comments/%d", taskId, commentId))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }
}