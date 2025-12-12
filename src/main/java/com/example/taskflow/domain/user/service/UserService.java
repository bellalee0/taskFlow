package com.example.taskflow.domain.user.service;

import com.example.taskflow.common.entity.*;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.model.enums.UserRole;
import com.example.taskflow.common.utils.PasswordEncoder;
import com.example.taskflow.domain.comment.repository.*;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.team.repository.TeamUserRepository;
import com.example.taskflow.domain.user.model.dto.UserDto;
import com.example.taskflow.domain.user.model.request.*;
import com.example.taskflow.domain.user.model.response.*;
import com.example.taskflow.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static com.example.taskflow.common.exception.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final TeamUserRepository teamUserRepository;

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
    @Transactional(readOnly = true)
    public UserGetProfileResponse getUser(Long id) {

        User user = userRepository.findUserById(id); //디폴트 메소드로

        return UserGetProfileResponse.from(UserDto.from(user));
    }

    //사용자 목록 조회
    @Transactional(readOnly = true)
    public List<UserListInquiryResponse> getUserList() {

        List<User> users = userRepository.findAll();

        return users.stream().map(user -> UserListInquiryResponse.from(UserDto.from(user))).toList();
    }


    //사용자 정보 수정
    @Transactional
    public UserUpdateInfoResponse updateUserInfo(Long id, UserUpdateInfoRequest request) {

        User user = userRepository.findUserById(id);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(AUTH_WRONG_EMAIL_AND_PASSWORD);
        }

        if (!user.getEmail().equals(request.getEmail())) {
            checkEmailExistence(request.getEmail());
        }

        user.updateUser(request);
        userRepository.saveAndFlush(user);

        return UserUpdateInfoResponse.from(UserDto.from(user));
    }

    //회원 탈퇴
    @Transactional
    public void deleteUser(Long id) {

        User user = userRepository.findUserById(id);

        List<Task> tasks = taskRepository.findAllByAssigneeId(user);
        tasks.stream().forEach(BaseEntity::updateIsDeleted);

        List<Comment> comments = commentRepository.findAllByUser(user);
        comments.stream().forEach(BaseEntity::updateIsDeleted);

        user.updateIsDeleted();
    }

    //추가 가능한 사용자 조회
    @Transactional(readOnly = true)
    public List<UserAvailableTeamResponse> findAvailableUsers(Long teamId) {

        List<User> users = userRepository.findAll();

        if(teamId == null) {
            return users.stream().map(user -> UserAvailableTeamResponse.from(UserDto.from(user))).toList();
        }

        List<TeamUser> teams = teamUserRepository.findByTeamId(teamId);
        List<User> userList = teams.stream().map(TeamUser::getUser).toList();

        return users.stream().filter(user -> !userList.contains(user))
                .map(user -> UserAvailableTeamResponse.from(UserDto.from(user))).toList();
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
