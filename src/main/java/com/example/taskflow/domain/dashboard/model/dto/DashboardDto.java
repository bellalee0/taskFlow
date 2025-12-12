package com.example.taskflow.domain.dashboard.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardDto {
    private final Long totalTasks;
    private final Long completedTasks;
    private final Long inProgressTasks;
    private final Long todoTasks;
    private final Long overdueTasks;
    private final double teamProgress;
    private final double completionRate;

    public static DashboardDto of(
            Long totalTasks,
            Long completedTasks,
            Long inProgressTasks,
            Long todoTasks,
            Long overdueTasks,
            double teamProgress,
            double completionRate
    ) {
        return new DashboardDto(
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