package com.example.taskflow.domain.team.model.request;

import com.example.taskflow.common.model.enums.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TeamCreateRequest {
    @NotBlank(message = ValidationMessage.TEAM_NAME_NOT_BLANK)
    private String name;
    private String description;
}
