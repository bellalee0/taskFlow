package com.example.taskflow.domain.dashboard.model.response;

import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.domain.team.model.dto.MemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DashboardUpcomingTasksResponse {

    private final Long id;
    private final MemberInfoDto assignee;
    private final String title;
    private final TaskStatus status;
    private final TaskPriority priority;
    private final LocalDateTime dueDate;

    public static DashboardUpcomingTasksResponse from(Task task, MemberInfoDto assignee) {
        return new DashboardUpcomingTasksResponse(
                task.getId(),
                assignee,
                task.getTitle(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate()
        );
    }
}