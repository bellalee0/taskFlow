package com.example.taskflow.domain.activities.model.response;

import com.example.taskflow.common.model.enums.LogType;
import com.example.taskflow.domain.activities.model.dto.LogDto;
import com.example.taskflow.domain.user.model.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogGetAllResponse {

    private Long id;
    private LogType type;
    private Long userId;
    private UserDto user;
    private Long taskId;
    private LocalDateTime timestamp;
    private String description;

    public static LogGetAllResponse from(LogDto logDto, UserDto userDto) {
        return new LogGetAllResponse(
            logDto.getId(),
            logDto.getType(),
            logDto.getUserId(),
            userDto,
            logDto.getTask() == null ? null : logDto.getTask().getId(),
            logDto.getLoggedDateTime(),
            logDto.getDescription()
        );
    }
}
