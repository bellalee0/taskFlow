package com.example.taskflow.domain.user.service;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.model.enums.UserRole;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.common.utils.PasswordEncoder;
import com.example.taskflow.domain.user.model.dto.UserDto;
import com.example.taskflow.domain.user.model.request.UserCreateRequest;
import com.example.taskflow.domain.user.model.request.UserUpdateInfoRequest;
import com.example.taskflow.domain.user.model.response.UserCreateResponse;
import com.example.taskflow.domain.user.model.response.UserGetProfileResponse;
import com.example.taskflow.domain.user.model.response.UserListInquiryResponse;
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

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(USER_USED_USERNAME);
        }

        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new CustomException(USER_USED_EMAIL);
        }

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
    public UserUpdateInfoRequest updateUserInfo(Long id, UserUpdateInfoRequest request) {

        User user = userRepository.findUserById(id);

        if (!user.getEmail().equals(request.getEmail())) {
            throw new CustomException(USER_USED_EMAIL);
        }

        //request Dto로 한번에
        user.updateName(request.getName());
        user.updateEmail(request.getEmail());

        //비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(AUTH_WRONG_EMAIL_AND_PASSWORD);
        }
        return request;
    }

    //회원 탈퇴
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findUserById(id);

        user.updateIsDeleted();


    }
}
