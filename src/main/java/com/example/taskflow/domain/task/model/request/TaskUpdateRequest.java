package com.example.taskflow.domain.task.model.request;

import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskUpdateRequest {
    @NotBlank(message = "제목과 담당자는 필수입니다.")
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    @NotNull(message = "제목과 담당자는 필수입니다.")
    private Long assigneeId;
    private LocalDateTime dueDate;
}
