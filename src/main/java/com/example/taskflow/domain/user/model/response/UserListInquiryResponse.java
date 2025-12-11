package com.example.taskflow.domain.user.model.response;

import com.example.taskflow.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserListInquiryResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final String role;
    private final LocalDateTime createdAt;

    public static UserListInquiryResponse from(UserDto user) {
        return new UserListInquiryResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getRole().toString(),
                user.getCreatedAt()
        );
    }
}
