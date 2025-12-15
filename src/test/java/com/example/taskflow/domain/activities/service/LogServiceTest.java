package com.example.taskflow.domain.activities.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.taskflow.common.entity.Log;
import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.model.enums.LogType;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.domain.activities.model.response.LogGetAllResponse;
import com.example.taskflow.domain.activities.model.response.LogGetMineResponse;
import com.example.taskflow.domain.activities.repository.LogRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import com.example.taskflow.fixture.TaskFixture;
import com.example.taskflow.fixture.UserFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {

    @Mock
    private LogRepository logRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LogService logService;

    @Test
    @DisplayName("전체 활동 로그 조회 테스트 - 성공")
    void getAllLogs() {

        // Given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        Log log = new Log(LogType.TASK_CREATED, user, task, LogType.TASK_CREATED.getDescription());
        ReflectionTestUtils.setField(log, "id", 1L);

        Page<Log> logPage = new PageImpl<>(List.of(log), pageable, 1);

        when(logRepository.findByFilters(pageable, null, null, null, null)).thenReturn(logPage);

        // When
        PageResponse<LogGetAllResponse> response = logService.getAllLogs(pageable, null, null, null, null);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().get(0).getUser().getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getContent().get(0).getTaskId()).isEqualTo(task.getId());
    }

    @Test
    @DisplayName("내 활동 로그 조회 테스트 - 성공")
    void getMyLogs() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        Log log = new Log(LogType.TASK_CREATED, user, task, LogType.TASK_CREATED.getDescription());
        ReflectionTestUtils.setField(log, "id", 1L);

        when(userRepository.findUserByUsername(anyString())).thenReturn(user);
        when(logRepository.findAllByUserId(anyLong())).thenReturn(List.of(log));

        // When
        List<LogGetMineResponse> response = logService.getMyLogs(user.getUsername());

        // Then
        assertThat(response).isNotNull();
        assertThat(response).hasSize(1);
        assertThat(response.get(0).getUser().getUsername()).isEqualTo(user.getUsername());
    }
}