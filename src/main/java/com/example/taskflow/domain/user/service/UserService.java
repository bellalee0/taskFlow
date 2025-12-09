package com.example.taskflow.domain.user.service;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.example.taskflow.common.entity.Team;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.domain.user.model.dto.UserDto;
import com.example.taskflow.domain.user.model.request.UserCreateRequest;
import com.example.taskflow.domain.user.model.response.UserCreateResponse;
import com.example.taskflow.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.taskflow.common.exception.ErrorMessage.USER_REQUEST_NOT_VALID_EMAIL_FORMAT;
import static com.example.taskflow.common.exception.ErrorMessage.USER_USED_USERNAME;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //회원가입
    public UserCreateResponse createUser(@Valid UserCreateRequest request) {
        if (userRepository.existsUserByUserName(request.getUserName())) {
            throw new CustomException(USER_USED_USERNAME);
        }

        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new CustomException(USER_REQUEST_NOT_VALID_EMAIL_FORMAT);
        }

//        String encodingPassword = passwordEncoder.encode(request.getPassword());


        User user = new User(
                request.getUserName(),
                request.getEmail(),
                request.getPassword(),  //encodingPassword,
                request.getName()
        );

        userRepository.save(user);

        return UserCreateResponse.from(UserDto.from(user));

    }
}
