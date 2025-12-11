package com.example.taskflow.domain.user.model.response;

import com.example.taskflow.common.model.enums.UserRole;
import com.example.taskflow.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserUpdateInfoResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final UserRole role;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;

    public static UserUpdateInfoResponse from(UserDto userDto) {
        return new UserUpdateInfoResponse(
            userDto.getId(),
            userDto.getUsername(),
            userDto.getEmail(),
            userDto.getName(),
            userDto.getRole(),
            userDto.getCreatedAt(),
            userDto.getModifiedAt()
        );
    }
}
