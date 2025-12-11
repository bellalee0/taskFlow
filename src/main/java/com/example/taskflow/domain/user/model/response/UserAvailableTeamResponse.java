package com.example.taskflow.domain.user.model.response;

import com.example.taskflow.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserAvailableTeamResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final String role;
    private final LocalDateTime createdAt;

    public static UserAvailableTeamResponse from(UserDto userDto) {
        return new UserAvailableTeamResponse(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getName(),
                userDto.getRole().toString(),
                userDto.getCreatedAt()
        );
    }


}
