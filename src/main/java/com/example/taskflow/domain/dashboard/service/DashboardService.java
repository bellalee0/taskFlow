package com.example.taskflow.domain.dashboard.service;

import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.domain.dashboard.model.response.DashboardGetStatsResponse;
import com.example.taskflow.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TaskRepository taskRepository;

    //region 대시보드 통계 기능
    @Transactional
    public DashboardGetStatsResponse getDashboardStats(Long userId) {

        LocalDateTime now = LocalDateTime.now();

        long totalTasks      = taskRepository.count();
        long completedTasks  = taskRepository.countByStatus(TaskStatus.DONE);
        long inProgressTasks = taskRepository.countByStatus(TaskStatus.IN_PROGRESS);
        long todoTasks       = taskRepository.countByStatus(TaskStatus.TODO);
        long overdueTasks    = taskRepository.countByStatusNotAndDueDateBefore(TaskStatus.DONE, now);

        double teamProgress =
                totalTasks == 0 ? 0.0 : (completedTasks * 100.0) / totalTasks;

        long userTotalTasks      = taskRepository.countByAssigneeIdId(userId);
        long userCompletedTasks  = taskRepository.countByAssigneeIdIdAndStatus(userId, TaskStatus.DONE);

        double completionRate =
                userTotalTasks == 0 ? 0.0 : (userCompletedTasks * 100.0) / userTotalTasks;

        return new DashboardGetStatsResponse(
                totalTasks,
                completedTasks,
                inProgressTasks,
                todoTasks,
                overdueTasks,
                teamProgress,
                completionRate
        );
    }
    //endregion

/*    //region 내 작업 요약
    public DashboardGetUserTaskSummaryResponse getUserTaskSummary(Long userId) {

    }
    //endregion*/

    }
