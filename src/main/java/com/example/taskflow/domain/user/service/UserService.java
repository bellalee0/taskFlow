package com.example.taskflow.domain.user.service;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.model.enums.UserRole;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.common.utils.PasswordEncoder;
import com.example.taskflow.domain.user.model.dto.UserDto;
import com.example.taskflow.domain.user.model.request.*;
import com.example.taskflow.domain.user.model.response.*;
import com.example.taskflow.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.example.taskflow.common.exception.ErrorMessage.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public UserCreateResponse createUser(@Valid UserCreateRequest request) {

        checkUsernameExistence(request.getUsername());
        checkEmailExistence(request.getEmail());

        String encodingPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                encodingPassword,
                request.getName(),
                UserRole.USER
        );

        userRepository.save(user);

        return UserCreateResponse.from(UserDto.from(user));
    }

    //사용자 정보 조회
    public UserGetProfileResponse getUser(Long id) {

        User user = userRepository.findUserById(id); //디폴트 메소드로

        return UserGetProfileResponse.from(UserDto.from(user));
    }

    //사용자 목록 조회
    public PageResponse<UserListInquiryResponse> getUserList(Pageable pageable) {

        Page<User> userList = userRepository.findAll(pageable);

        Page<UserListInquiryResponse> userDtoList = userList.map(UserListInquiryResponse::new);

        return PageResponse.from(userDtoList);
    }

    //사용자 정보 수정
    public UserUpdateInfoResponse updateUserInfo(Long id, UserUpdateInfoRequest request) {

        User user = userRepository.findUserById(id);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(AUTH_WRONG_EMAIL_AND_PASSWORD);
        }

        checkEmailExistence(request.getEmail());

        user.updateUser(request);
        userRepository.saveAndFlush(user);

        return UserUpdateInfoResponse.from(UserDto.from(user));
    }

    //회원 탈퇴
    @Transactional
    public void deleteUser(Long id) {

        User user = userRepository.findUserById(id);

        // TODO: task, comment와 병합 후, 유저의 comment, task 삭제 처리

        user.updateIsDeleted();
    }

    // username 중복 여부 확인
    private void checkUsernameExistence(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new CustomException(USER_USED_USERNAME);
        }
    }

    // 이메일 중복 여부 확인
    private void checkEmailExistence(String email) {
        if (userRepository.existsUserByEmail(email)) {
            throw new CustomException(USER_USED_EMAIL);
        }
    }
}
