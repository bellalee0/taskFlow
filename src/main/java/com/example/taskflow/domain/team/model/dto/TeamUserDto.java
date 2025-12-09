package com.example.taskflow.domain.team.model.dto;

import com.example.taskflow.common.entity.TeamUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamUserDto {

    private Long id;
    private Long teamId;
    private Long userId;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

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
