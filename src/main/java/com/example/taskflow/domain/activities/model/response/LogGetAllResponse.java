package com.example.taskflow.domain.activities.model.response;

import com.example.taskflow.common.model.enums.LogType;
import com.example.taskflow.domain.activities.model.dto.LogDto;
import com.example.taskflow.domain.team.model.dto.MemberInfoDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogGetAllResponse {

    private Long id;
    private LogType type;
    private Long userId;
    private MemberInfoDto user;
    private Long taskId;
    private LocalDateTime timestamp;
    private String description;

    public static LogGetAllResponse from(LogDto logDto, MemberInfoDto userDto) {
        return new LogGetAllResponse(
            logDto.getId(),
            logDto.getType(),
            logDto.getUserId(),
            userDto,
            logDto.getTask().getId(),
            logDto.getLoggedDateTime(),
            logDto.getDescription()
        );
    }
}
