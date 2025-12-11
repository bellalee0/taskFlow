package com.example.taskflow.domain.dashboard.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardStatsResponse {
    private long totalTasks;
    private long completedTasks;
    private long inProgressTasks;
    private long todoTasks;
    private long overdueTasks;
    private double teamProgress;
    private double completionRate;
}
