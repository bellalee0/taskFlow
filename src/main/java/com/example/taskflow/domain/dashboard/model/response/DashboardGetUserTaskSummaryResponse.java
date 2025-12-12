
package com.example.taskflow.domain.dashboard.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DashboardGetUserTaskSummaryResponse {
    private final List<TodayTasksResponse> todayTasks;
    private final List<UpcomingTasksResponse> upcomingTasks;
    private final List<OverdueTasksResponse> overdueTasks;

    public static DashboardGetUserTaskSummaryResponse from(
            List<TodayTasksResponse> todayTasks,
            List<UpcomingTasksResponse> upcomingTasks,
            List<OverdueTasksResponse> overdueTasks
    ) {
        return new DashboardGetUserTaskSummaryResponse(todayTasks, upcomingTasks, overdueTasks);
    }
}

