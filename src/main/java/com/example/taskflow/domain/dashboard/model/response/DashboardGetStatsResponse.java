package com.example.taskflow.domain.dashboard.model.response;

import com.example.taskflow.domain.dashboard.model.dto.DashboardStatsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardGetStatsResponse {

    private final Long totalTasks;
    private final Long completedTasks;
    private final Long inProgressTasks;
    private final Long todoTasks;
    private final Long overdueTasks;
    private final Double teamProgress;
    private final Double completionRate;

    public static DashboardGetStatsResponse from(DashboardStatsDto dto) {
        return new DashboardGetStatsResponse(
                dto.getTotalTasks(),
                dto.getCompletedTasks(),
                dto.getInProgressTasks(),
                dto.getTodoTasks(),
                dto.getOverdueTasks(),
                dto.getTeamProgress(),
                dto.getCompletionRate()
        );
    }
}
