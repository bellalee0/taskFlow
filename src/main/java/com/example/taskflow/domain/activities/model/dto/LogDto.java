package com.example.taskflow.domain.activities.model.dto;

import com.example.taskflow.common.entity.Log;
import com.example.taskflow.common.model.enums.LogType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogDto {

    private Long id;
    private LogType type;
    private Long userId;
    private Long taskId;
    private Long commentId;
    private LocalDateTime loggedDateTime;
    private String description;

    public static LogDto from(Log log) {
        return new LogDto(
            log.getId(),
            log.getType(),
            log.getUser().getId(),
            log.getTask() == null ? 0L : log.getTask().getId(),
            log.getComment().getId() == null ? 0L : log.getComment().getId(),
            log.getLoggedDateTime(),
            log.getDescription()
        );
    }
}
