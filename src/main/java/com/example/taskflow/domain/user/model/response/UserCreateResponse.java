package com.example.taskflow.domain.user.model.response;

import com.example.taskflow.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateResponse {

    private Long id;
    private String userName;
    private String email;


    public static UserCreateResponse from(UserDto userDto) {
        return new UserCreateResponse(
                userDto.getId(),
                userDto.getUserName(),
                userDto.getEmail(),
                userDto.getName()
        )
    }
}
