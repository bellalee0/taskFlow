package com.example.taskflow.domain.dashboard.service;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.domain.dashboard.model.dto.DashboardStatsDto;
import com.example.taskflow.domain.dashboard.model.response.*;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    //region 대시보드 통계 기능
    @Transactional(readOnly = true)
    public DashboardGetStatsResponse getDashboardStats(String username) {

        LocalDate today = LocalDate.now();
        LocalDateTime endOfToday = today.atTime(LocalTime.MAX);

        long totalTasks = taskRepository.count();
        long completedTasks = taskRepository.countByStatus(TaskStatus.DONE);
        long inProgressTasks = taskRepository.countByStatus(TaskStatus.IN_PROGRESS);
        long todoTasks = taskRepository.countByStatus(TaskStatus.TODO);
        long overdueTasks = taskRepository.countByStatusNotAndDueDateBefore(TaskStatus.DONE, endOfToday);

        double teamProgress =
                totalTasks == 0 ? 0.0 : (completedTasks * 100.0) / totalTasks;

        User user = userRepository.findUserByUsername(username);

        long userTotalTasks = taskRepository.countByAssigneeIdId(user.getId());
        long userCompletedTasks = taskRepository.countByAssigneeIdIdAndStatus(user.getId(), TaskStatus.DONE);

        double completionRate =
                userTotalTasks == 0 ? 0.0 : (userCompletedTasks * 100.0) / userTotalTasks;

        DashboardStatsDto dto = DashboardStatsDto.from(
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
    public DashboardGetUserTaskSummaryResponse getUserTaskSummary(String username) {

        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.atTime(LocalTime.MAX);

        User user = userRepository.findUserByUsername(username);

        List<DashboardTodayTasksResponse> todayTasks = taskRepository.findByAssigneeIdIdAndDueDateBetween(user.getId(), startOfToday, endOfToday).stream()
                .map(DashboardTodayTasksResponse::from)
                .toList();

        List<DashboardUpcomingTasksResponse> upcomingTasks = taskRepository.findByAssigneeIdIdAndDueDateAfterAndStatusNot(user.getId(), endOfToday, TaskStatus.DONE).stream()
                .map(DashboardUpcomingTasksResponse::from)
                .toList();

        List<DashboardOverdueTasksResponse> overdueTasks = taskRepository.findByAssigneeIdIdAndDueDateBeforeAndStatusNot(user.getId(), startOfToday, TaskStatus.DONE).stream()
                .map(DashboardOverdueTasksResponse::from)
                .toList();

        return DashboardGetUserTaskSummaryResponse.from(todayTasks, upcomingTasks, overdueTasks);
    }
    //endregion

    //region 주간 작업 추세
    @Transactional(readOnly = true)
    public List<DashboardGetWeeklyTrendResponse> getWeeklyTrend() {

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter toKoreanDay = DateTimeFormatter.ofPattern("E", Locale.KOREA);

        List<DashboardGetWeeklyTrendResponse> weeklyTrendList = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate weeklyDay = today.minusDays(i);

            String name = weeklyDay.format(toKoreanDay);
            Long completed = taskRepository.countByCompletedDateTimeBetween(weeklyDay.atStartOfDay(), weeklyDay.atTime(LocalTime.MAX));
            Long tasks = taskRepository.countByCreatedAtBeforeAndStatusNot(weeklyDay.atStartOfDay(), TaskStatus.DONE) + completed;
            String date = weeklyDay.format(formatter);

            weeklyTrendList.add(new DashboardGetWeeklyTrendResponse(name, tasks, completed, date));
        }

        return weeklyTrendList;
    }
    //endregion

}
