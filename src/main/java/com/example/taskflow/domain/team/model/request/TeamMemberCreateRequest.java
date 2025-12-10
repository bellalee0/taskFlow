package com.example.taskflow.domain.team.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TeamMemberCreateRequest {
    @NotNull(message = "userId는 필수입니다.")
    private Long userId;
}
