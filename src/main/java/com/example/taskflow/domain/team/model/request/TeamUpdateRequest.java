package com.example.taskflow.domain.team.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TeamUpdateRequest {
    @NotNull(message = "팀 이름은 필수입니다.")
    private String name;
    private String description;
}
