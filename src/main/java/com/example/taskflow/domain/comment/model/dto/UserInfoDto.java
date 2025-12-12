package com.example.taskflow.domain.comment.model.dto;

import com.example.taskflow.common.model.enums.UserRole;
import com.example.taskflow.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private final Long id;
    private final String username;
    private final String name;
    private final String email;
    private final UserRole role;

    public static UserInfoDto from(UserDto dto) {
        return new UserInfoDto(
            dto.getId(),
            dto.getUsername(),
            dto.getName(),
            dto.getEmail(),
            dto.getRole()
        );
    }
}