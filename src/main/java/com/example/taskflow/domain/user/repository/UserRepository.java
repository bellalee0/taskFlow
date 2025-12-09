package com.example.taskflow.domain.user.repository;

import com.example.taskflow.common.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    //유저이름 존재 여부
    boolean existsUserByUserName(String userName);

    //이메일 존재 여부
    boolean existsUserByEmail(String email);
}
