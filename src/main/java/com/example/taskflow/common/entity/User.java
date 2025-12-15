package com.example.taskflow.common.entity;

import com.example.taskflow.common.model.enums.UserRole;
import com.example.taskflow.domain.user.model.request.UserUpdateInfoRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String username, String email, String password, String name, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public void updateUser(UserUpdateInfoRequest request) {
        this.email = request.getEmail();
        this.name = request.getName();
    }
}
