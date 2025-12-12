package com.example.taskflow.domain.search.model.dto;

import com.example.taskflow.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSearchDto {

    private final Long id;
    private final String name;
    private final String username;

    public static UserSearchDto from(UserDto userDto) {
        return new UserSearchDto(
                userDto.getId(),
                userDto.getName(),
                userDto.getUsername()
        );
    }

}
