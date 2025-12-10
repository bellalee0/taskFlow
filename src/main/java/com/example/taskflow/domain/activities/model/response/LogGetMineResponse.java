package com.example.taskflow.domain.activities.model.response;

import com.example.taskflow.common.model.enums.LogType;
import com.example.taskflow.domain.activities.model.dto.LogDto;
import com.example.taskflow.domain.user.model.dto.UserDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogGetMineResponse {

    private Long id;
    private Long userId;
    private UserDto user;
    private String action;
    private LogType targetType;
    private Long targetId;
    private String description;
    private LocalDateTime createdAt;

    public static LogGetMineResponse from(LogDto logDto, UserDto userDto) {
        return new LogGetMineResponse(
            logDto.getId(),
            logDto.getUserId(),
            userDto,
            logDto.getType().getAction(),
            logDto.getType(),
            logDto.getTask().getId(),
            logDto.getDescription(),
            logDto.getLoggedDateTime()
        );
    }
}
