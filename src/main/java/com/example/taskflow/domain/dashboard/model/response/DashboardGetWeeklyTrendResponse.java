package com.example.taskflow.domain.dashboard.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardGetWeeklyTrendResponse {

    private final String name;
    private final Long tasks;
    private final Long completed;
    private final String date;

    public static DashboardGetWeeklyTrendResponse from(
            String name,
            Long tasks,
            Long completed,
            String date
    ) {
        return new DashboardGetWeeklyTrendResponse(name, tasks, completed, date);
    }
}
