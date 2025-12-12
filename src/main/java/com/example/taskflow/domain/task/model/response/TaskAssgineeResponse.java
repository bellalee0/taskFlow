package com.example.taskflow.domain.task.model.response;

import com.example.taskflow.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskAssgineeResponse {
    private final Long id;
    private final String username;
    private final String name;

    public static TaskAssgineeResponse from(UserDto dto) {
        return new TaskAssgineeResponse(
                dto.getId(),
                dto.getUsername(),
                dto.getName());
    }
}

