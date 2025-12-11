package com.example.taskflow.domain.dashboard.service;

import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.domain.dashboard.model.response.DashboardStatsResponse;
import com.example.taskflow.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TaskRepository taskRepository;

    public DashboardStatsResponse getDashboardStats(/*AuthUser authUser*/ Long userId) {

        /*Long userId = authUser.getId();*/
        LocalDateTime now = LocalDateTime.now();

        long totalTasks      = taskRepository.count(); /*task Entity도 isDeleted적용 필요*/
        long completedTasks  = taskRepository.countByStatus(TaskStatus.DONE);
        long inProgressTasks = taskRepository.countByStatus(TaskStatus.IN_PROGRESS);
        long todoTasks       = taskRepository.countByStatus(TaskStatus.TODO);
        long overdueTasks    = taskRepository.countByStatusNotAndDueDateBefore(TaskStatus.DONE, now);

        double teamProgress =
                totalTasks == 0 ? 0.0 : (completedTasks * 100.0) / totalTasks;

        long userTotalTasks      = taskRepository.countByAssigneeIdId(/*authUser.getId();*/userId);
        long userCompletedTasks  = taskRepository.countByAssigneeIdIdAndStatus(/*authUser.getId();*/userId, TaskStatus.DONE);

        double completionRate =
                userTotalTasks == 0 ? 0.0 : (userCompletedTasks * 100.0) / userTotalTasks;

        return new DashboardStatsResponse(
                totalTasks,
                completedTasks,
                inProgressTasks,
                todoTasks,
                overdueTasks,
                teamProgress,
                completionRate
        );
    }
}
