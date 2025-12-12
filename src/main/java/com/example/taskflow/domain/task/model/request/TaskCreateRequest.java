package com.example.taskflow.domain.task.model.request;

import com.example.taskflow.common.model.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskCreateRequest {
    @NotBlank(message = "제목과 담당자는 필수입니다.")
    private String title;
    private String description;
    private TaskPriority priority;
    @NotNull(message = "제목과 담당자는 필수입니다.")
    private Long assigneeId;
    private LocalDateTime dueDate;
}
