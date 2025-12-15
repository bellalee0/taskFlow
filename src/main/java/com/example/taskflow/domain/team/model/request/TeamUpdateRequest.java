package com.example.taskflow.domain.team.model.request;

import com.example.taskflow.common.exception.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TeamUpdateRequest {
    @NotBlank(message = ValidationMessage.TEAM_NAME_NOT_BLANK)
    private String name;
    private String description;
}
