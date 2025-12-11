package com.example.taskflow.domain.team.model.dto;

import com.example.taskflow.common.entity.TeamUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TeamUserDto {

    private final Long id;
    private final Long teamId;
    private final Long userId;
    private final boolean isDeleted;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static TeamUserDto from(TeamUser teamUser) {
        return new TeamUserDto(
                teamUser.getId(),
                teamUser.getTeam().getId(),
                teamUser.getUser().getId(),
                teamUser.isDeleted(),
                teamUser.getCreatedAt(),
                teamUser.getModifiedAt()
        );
    }
}
