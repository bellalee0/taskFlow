package com.example.taskflow.domain.team.model.request;

import com.example.taskflow.common.model.enums.ValidationMessage;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TeamMemberCreateRequest {
    @NotNull(message = ValidationMessage.USER_ID_NOT_NULL)
    private Long userId;
}
