package com.example.taskflow.domain.dashboard.service;

import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.domain.dashboard.model.dto.DashboardDto;
import com.example.taskflow.domain.dashboard.model.response.*;
import com.example.taskflow.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TaskRepository taskRepository;

    //region 대시보드 통계 기능
    @Transactional(readOnly = true)
    public DashboardGetStatsResponse getDashboardStats(Long userId) {

        LocalDateTime now = LocalDateTime.now();

        long totalTasks = taskRepository.count();
        long completedTasks = taskRepository.countByStatus(TaskStatus.DONE);
        long inProgressTasks = taskRepository.countByStatus(TaskStatus.IN_PROGRESS);
        long todoTasks = taskRepository.countByStatus(TaskStatus.TODO);
        long overdueTasks = taskRepository.countByStatusNotAndDueDateBefore(TaskStatus.DONE, now);

        double teamProgress =
                totalTasks == 0 ? 0.0 : (completedTasks * 100.0) / totalTasks;

        long userTotalTasks = taskRepository.countByAssigneeIdId(userId);
        long userCompletedTasks = taskRepository.countByAssigneeIdIdAndStatus(userId, TaskStatus.DONE);

        double completionRate =
                userTotalTasks == 0 ? 0.0 : (userCompletedTasks * 100.0) / userTotalTasks;

        DashboardDto dto = DashboardDto.of(
                totalTasks,
                completedTasks,
                inProgressTasks,
                todoTasks,
                overdueTasks,
                teamProgress,
                completionRate
        );

        return DashboardGetStatsResponse.from(dto);
    }
    //endregion

    //region 내 작업 요약
    @Transactional(readOnly = true)
    public DashboardGetUserTaskSummaryResponse getUserTaskSummary(Long userId) {

        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.atTime(LocalTime.MAX);

        List<TodayTasksResponse> todayTasks = taskRepository.findByAssigneeIdIdAndDueDateBetween(userId, startOfToday, endOfToday).stream()
                .map(TodayTasksResponse::from)
                .toList();

        List<UpcomingTasksResponse> upcomingTasks = taskRepository.findByAssigneeIdIdAndDueDateAfterAndStatusNot(userId, endOfToday, TaskStatus.DONE).stream()
                .map(UpcomingTasksResponse::from)
                .toList();

        List<OverdueTasksResponse> overdueTasks = taskRepository.findByAssigneeIdIdAndDueDateBeforeAndStatusNot(userId, startOfToday, TaskStatus.DONE).stream()
                .map(OverdueTasksResponse::from)
                .toList();

        return DashboardGetUserTaskSummaryResponse.from(todayTasks, upcomingTasks, overdueTasks);
    }
    //endregion

}
