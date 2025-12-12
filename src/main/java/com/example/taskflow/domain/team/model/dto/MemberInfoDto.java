package com.example.taskflow.domain.team.model.dto;

import com.example.taskflow.common.model.enums.UserRole;
import com.example.taskflow.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberInfoDto {
    private final Long id;
    private final String username;
    private final String name;
    private final String email;
    private final UserRole role;
    private final LocalDateTime createdAt;

    public static MemberInfoDto from(UserDto dto) {
        return new MemberInfoDto(
                dto.getId(),
                dto.getUsername(),
                dto.getName(),
                dto.getEmail(),
                dto.getRole(),
                dto.getCreatedAt()
        );
    }
}
