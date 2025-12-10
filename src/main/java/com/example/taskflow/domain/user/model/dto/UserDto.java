package com.example.taskflow.domain.user.model.dto;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String userName;
    private String email;
    private String password;
    private String name;
    private UserRole role;
    private LocalDateTime createdAt;

    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
