package com.example.taskflow.domain.user.service;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.model.enums.UserRole;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.common.utils.PasswordEncoder;
import com.example.taskflow.domain.user.model.dto.UserDto;
import com.example.taskflow.domain.user.model.request.UserCreateRequest;
import com.example.taskflow.domain.user.model.request.UserDeleteRequest;
import com.example.taskflow.domain.user.model.request.UserUpdateInfoRequest;
import com.example.taskflow.domain.user.model.response.UserCreateResponse;
import com.example.taskflow.domain.user.model.response.UserGetProfileResponse;
import com.example.taskflow.domain.user.model.response.UserListInquiryResponse;
import com.example.taskflow.domain.user.model.response.UserUpdateInfoResponse;
import com.example.taskflow.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.example.taskflow.common.exception.ErrorMessage.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public UserCreateResponse createUser(@Valid UserCreateRequest request) {

        if (userRepository.existsUserByUserName(request.getUserName())) {
            throw new CustomException(USER_USED_USERNAME);
        }

        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new CustomException(USER_USED_EMAIL);
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
    @Transactional(readOnly = true)
    public UserGetProfileResponse getUser(Long id) {

        User user = userRepository.findUserById(id); //디폴트 메소드로

        return UserGetProfileResponse.from(UserDto.from(user));
    }

    //사용자 목록 조회
    @Transactional(readOnly = true)
    public PageResponse<UserListInquiryResponse> getUserList(Pageable pageable) {

        Page<User> userList = userRepository.findAll(pageable);

        Page<UserListInquiryResponse> userDtoList = userList.map(UserListInquiryResponse::new);

        return PageResponse.from(userDtoList);
    }

    //사용자 정보 수정
    @Transactional
    public UserUpdateInfoResponse updateUserInfo(Long id, UserUpdateInfoRequest request) {

        User user = userRepository.findUserById(id);

        if (!user.getName().equals(request.getName())) {
            throw new CustomException(USER_USED_USERNAME);
        }
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
        return new UserUpdateInfoResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    ;

    //회원탈퇴시 아람님이 만든 패스워드인코더로

/**
 * //1. 아이디 먼저 찾고
 * User user = userRepository.findById(request).orElseThrow()
 * <p>
 * 2. 찾은 아이디에서 비밀번호를 내꺼서  입력한 비밀번호와 비교
 * if (!PasswordEncoder.matches(request.getPassword(), user.getPassword())) {
 * throw new CustomException(UNAUTHORIZED_WRONG_PASSWORD);
 * }
 * }
 */
}

