package com.example.taskflow.domain.dashboard.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardStatsDto {

    private final Long totalTasks;
    private final Long completedTasks;
    private final Long inProgressTasks;
    private final Long todoTasks;
    private final Long overdueTasks;
    private final Double teamProgress;
    private final Double completionRate;

    public static DashboardStatsDto from(
            Long totalTasks,
            Long completedTasks,
            Long inProgressTasks,
            Long todoTasks,
            Long overdueTasks,
            Double teamProgress,
            Double completionRate
    ) {
        return new DashboardStatsDto(
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