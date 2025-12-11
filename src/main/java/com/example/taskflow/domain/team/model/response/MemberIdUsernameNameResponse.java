package com.example.taskflow.domain.team.model.response;

import com.example.taskflow.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberIdUsernameNameResponse {
    private final Long id;
    private final String username;
    private final String name;

    public static MemberIdUsernameNameResponse from(UserDto dto) {
        return new MemberIdUsernameNameResponse(dto.getId(), dto.getUsername(), dto.getName());
    }
}
