
package com.example.taskflow.domain.dashboard.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DashboardGetUserTaskSummaryResponse {

    private final List<DashboardTodayTasksResponse> todayTasks;
    private final List<DashboardUpcomingTasksResponse> upcomingTasks;
    private final List<DashboardOverdueTasksResponse> overdueTasks;

    public static DashboardGetUserTaskSummaryResponse from(
            List<DashboardTodayTasksResponse> todayTasks,
            List<DashboardUpcomingTasksResponse> upcomingTasks,
            List<DashboardOverdueTasksResponse> overdueTasks
    ) {
        return new DashboardGetUserTaskSummaryResponse(todayTasks, upcomingTasks, overdueTasks);
    }
}

