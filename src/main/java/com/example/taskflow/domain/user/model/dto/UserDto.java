package com.example.taskflow.domain.user.model.dto;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserDto {

    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private final String name;
    private final UserRole role;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final boolean isDeleted;

    public static UserDto from(User user) {
        return new UserDto(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getName(),
            user.getRole(),
            user.getCreatedAt(),
            user.getModifiedAt(),
            user.isDeleted()
        );
    }
}
