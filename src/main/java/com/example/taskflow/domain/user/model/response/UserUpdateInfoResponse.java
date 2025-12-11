package com.example.taskflow.domain.user.model.response;

import com.example.taskflow.common.model.enums.UserRole;
import com.example.taskflow.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateInfoResponse {

    private Long id;
    private String username;
    private String email;
    private String name;
    private UserRole role;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

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
