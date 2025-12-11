package com.example.taskflow.domain.user.model.response;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateInfoResponse {

    private Long id;
    private String userName;
    private String email;
    private String name;
    private UserRole role;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
