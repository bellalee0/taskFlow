package com.example.taskflow.domain.team.model.response;

import com.example.taskflow.common.model.enums.UserRole;
import com.example.taskflow.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberIdUsernameNameEmailRoleResponse {
    private final Long id;
    private final String username;
    private final String name;
    private final String email;
    private final UserRole role;

    public static MemberIdUsernameNameEmailRoleResponse from(UserDto dto) {
        return new MemberIdUsernameNameEmailRoleResponse(
                dto.getId(),
                dto.getUserName(),
                dto.getName(),
                dto.getEmail(),
                dto.getRole()
        );
    }
}
