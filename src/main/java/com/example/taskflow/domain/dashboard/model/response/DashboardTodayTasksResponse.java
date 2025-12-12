package com.example.taskflow.domain.dashboard.model.response;

import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DashboardTodayTasksResponse {

    private final Long id;
    private final String title;
    private final TaskStatus status;
    private final TaskPriority priority;
    private final LocalDateTime dueDate;

    public static DashboardTodayTasksResponse from(Task task) {
        return new DashboardTodayTasksResponse(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate()
        );
    }
}