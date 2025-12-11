package com.example.taskflow.domain.user.model.response;

import com.example.taskflow.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserListInquiryResponse {

    private Long id;
    private String username;
    private String email;
    private String name;
    private String role;
    private LocalDateTime createdAt;

    public UserListInquiryResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole().toString();
        this.createdAt = user.getCreatedAt();
    }

    public static UserListInquiryResponse from(User user) {
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
