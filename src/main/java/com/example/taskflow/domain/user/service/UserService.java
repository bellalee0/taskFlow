package com.example.taskflow.domain.user.service;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.model.enums.UserRole;
import com.example.taskflow.common.utils.PasswordEncoder;
import com.example.taskflow.domain.user.model.dto.UserDto;
import com.example.taskflow.domain.user.model.request.UserCreateRequest;
import com.example.taskflow.domain.user.model.request.UserInformationModyifyingRequest;
import com.example.taskflow.domain.user.model.response.UserCreateResponse;
import com.example.taskflow.domain.user.model.response.UserListInquiryResponse;
import com.example.taskflow.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.taskflow.common.exception.ErrorMessage.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public UserCreateResponse createUser(@Valid UserCreateRequest request) {
        if (userRepository.existsUserByUserName(request.getUserName())) {
            throw new CustomException(USER_USED_USERNAME);
        }

        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new CustomException(USER_REQUEST_NOT_VALID_EMAIL_FORMAT);
        }

       String encodingPassword = passwordEncoder.encode(request.getPassword());


        User user = new User(
                request.getUserName(),
                request.getEmail(),
                encodingPassword,
                request.getName(),
                UserRole.USER
        );

        userRepository.save(user);

        return UserCreateResponse.from(UserDto.from(user));

    }

    //사용자 정보 조회
    public UserCreateResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return UserCreateResponse.from(UserDto.from(user));

    }

    //사용자 목록 조회
    public Page<UserListInquiryResponse> getUserList(Pageable pageable) {
        Page<User> userList = userRepository.findAll(pageable);
        return userList.map(UserListInquiryResponse::new);
    }
//
//    //사용자 정보 수정
//    public UserInformationModyifyingRequest updateUserInfo(Long id, UserInformationModyifyingRequest request) {
//
//        if(userRepository.findByEmail(()).equalse )
//
//        User user = userRepository.findById()
//    }


}
