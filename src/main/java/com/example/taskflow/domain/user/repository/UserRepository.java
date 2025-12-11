package com.example.taskflow.domain.user.repository;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import static com.example.taskflow.common.exception.ErrorMessage.USER_NOT_FOUND;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsUserByEmail(String email);

    Optional<User> findByUsername(String username);

    default User findUserById(Long id) {
        return findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    default User findUserByUsername(String username) {
        return findByUsername(username)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
