package com.example.taskflow.domain.dashboard.model.response;

import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DashboardUpcomingTasksResponse {

    private final Long id;
    private final String assignee;
    private final String title;
    private final TaskStatus status;
    private final TaskPriority priority;
    private final LocalDateTime dueDate;

    public static DashboardUpcomingTasksResponse from(Task task) {
        return new DashboardUpcomingTasksResponse(
                task.getId(),
                task.getAssigneeId().getName(),
                task.getTitle(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate()
        );
    }
}