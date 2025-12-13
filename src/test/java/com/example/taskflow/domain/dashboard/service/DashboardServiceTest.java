package com.example.taskflow.domain.dashboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.domain.dashboard.model.response.DashboardGetStatsResponse;
import com.example.taskflow.domain.dashboard.model.response.DashboardGetUserTaskSummaryResponse;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import com.example.taskflow.fixture.TaskFixture;
import com.example.taskflow.fixture.UserFixture;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    @DisplayName("대시보드 통계 기능 테스트 - 성공")
    void getDashboardStats() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        when(taskRepository.count()).thenReturn(5L);
        when(taskRepository.countByStatus(TaskStatus.DONE)).thenReturn(2L);
        when(taskRepository.countByStatus(TaskStatus.IN_PROGRESS)).thenReturn(1L);
        when(taskRepository.countByStatus(TaskStatus.TODO)).thenReturn(2L);
        when(taskRepository.countByStatusNotAndDueDateBefore(TaskStatus.DONE, LocalDate.now().atTime(LocalTime.MAX))).thenReturn(2L);
        when(userRepository.findUserByUsername(anyString())).thenReturn(user);
        when(taskRepository.countByAssigneeIdId(1L)).thenReturn(3L);
        when(taskRepository.countByAssigneeIdIdAndStatus(1L, TaskStatus.DONE)).thenReturn(1L);

        // When
        DashboardGetStatsResponse response = dashboardService.getDashboardStats(user.getUsername());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getTotalTasks()).isEqualTo(5L);
        assertThat(response.getTeamProgress()).isEqualTo(Math.round((2L * 100.0) / 5L));
        assertThat(response.getCompletionRate()).isEqualTo(Math.round((1L * 100.0) / 3L));
    }

    @Test
    @DisplayName("내 작업 요약 테스트 - 성공")
    void getUserTaskSummary_success() {

        // Given
        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.atTime(LocalTime.MAX);

        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task todayTask = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(todayTask, "id", 1L);
        ReflectionTestUtils.setField(todayTask, "dueDate", LocalDateTime.now());

        Task upcomingTask = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(upcomingTask, "id", 2L);

        Task overdueTask = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(overdueTask, "id", 3L);
        ReflectionTestUtils.setField(todayTask, "dueDate", LocalDateTime.now().minusDays(2));

        when(userRepository.findUserByUsername(anyString())).thenReturn(user);
        when(taskRepository.findByAssigneeIdIdAndDueDateBetween(user.getId(), startOfToday, endOfToday)).thenReturn(List.of(todayTask));
        when(taskRepository.findByAssigneeIdIdAndDueDateAfterAndStatusNot(user.getId(), endOfToday, TaskStatus.DONE)).thenReturn(List.of(upcomingTask));
        when(taskRepository.findByAssigneeIdIdAndDueDateBeforeAndStatusNot(user.getId(), startOfToday, TaskStatus.DONE)).thenReturn(List.of(overdueTask));

        // When
        DashboardGetUserTaskSummaryResponse response = dashboardService.getUserTaskSummary(user.getUsername());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getTodayTasks().size()).isEqualTo(1);
        assertThat(response.getTodayTasks().get(0).getAssignee().getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getTodayTasks().get(0).getDueDate()).isEqualTo(todayTask.getDueDate());
        assertThat(response.getUpcomingTasks().size()).isEqualTo(1);
        assertThat(response.getUpcomingTasks().get(0).getDueDate()).isEqualTo(upcomingTask.getDueDate());
        assertThat(response.getOverdueTasks().size()).isEqualTo(1);
        assertThat(response.getOverdueTasks().get(0).getDueDate()).isEqualTo(overdueTask.getDueDate());
    }

    @Test
    @Disabled
    @DisplayName("주간 작업 추세 테스트 - 성공")
    void getWeeklyTrend() {

    }
}