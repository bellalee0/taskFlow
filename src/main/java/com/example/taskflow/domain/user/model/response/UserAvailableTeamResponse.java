package com.example.taskflow.domain.user.model.response;

import com.example.taskflow.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAvailableTeamResponse {

    private Long id;
    private String username;
    private String email;
    private String name;
    private String role;
    private LocalDateTime createdAt;

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
