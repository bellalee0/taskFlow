package com.example.taskflow.domain.user.repository;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import static com.example.taskflow.common.exception.ErrorMessage.USER_NOT_FOUND;

public interface UserRepository extends JpaRepository<User, Long> {

    //유저이름 존재 여부
    boolean existsUserByUserName(String userName);

    //이메일 존재 여부
    boolean existsUserByEmail(String email);



    default User findUserById(Long id) {
        return findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    Optional<User> findByUserName(String username);

//    default User findUserByEmail(String email) {
//        return findUserByEmail(email)
//                .orElseThrow(() -> new CustomException(USER_NOT_EMAIL_FOUND));
//    }
}



