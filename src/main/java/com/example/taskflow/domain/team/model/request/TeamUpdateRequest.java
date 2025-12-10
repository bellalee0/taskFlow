package com.example.taskflow.domain.team.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TeamUpdateRequest {
    @NotBlank(message = "팀 이름은 필수입니다.")
    private String name;
    private String description;
}
