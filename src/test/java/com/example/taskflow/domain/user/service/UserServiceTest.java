package com.example.taskflow.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.utils.PasswordEncoder;
import com.example.taskflow.domain.user.model.request.UserCreateRequest;
import com.example.taskflow.domain.user.model.request.UserUpdateInfoRequest;
import com.example.taskflow.domain.user.model.response.UserCreateResponse;
import com.example.taskflow.domain.user.model.response.UserGetProfileResponse;
import com.example.taskflow.domain.user.model.response.UserListInquiryResponse;
import com.example.taskflow.domain.user.model.response.UserUpdateInfoResponse;
import com.example.taskflow.domain.user.repository.UserRepository;
import com.example.taskflow.fixture.UserFixture;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    @Test
    @DisplayName("회원가입 테스트 - 성공: 유효한 내용 입력")
    void createUser_success() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        UserCreateRequest request = new UserCreateRequest();
        ReflectionTestUtils.setField(request, "username", UserFixture.DEFAULT_USERNAME);
        ReflectionTestUtils.setField(request, "email", UserFixture.DEFAULT_EMAIL);
        ReflectionTestUtils.setField(request, "password", UserFixture.DEFAULT_PASSWORD);
        ReflectionTestUtils.setField(request, "name", UserFixture.DEFAULT_NAME);

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsUserByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(UserFixture.ENCODED_PASSWORD);

        // When
        UserCreateResponse response = userService.createUser(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getEmail()).isEqualTo(user.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입 테스트 - 실패: 중복된 유저명 입력")
    void createUser_failure_sameUsername() {

        // Given
        UserCreateRequest request = new UserCreateRequest();
        ReflectionTestUtils.setField(request, "username", UserFixture.DEFAULT_USERNAME);
        ReflectionTestUtils.setField(request, "email", UserFixture.DEFAULT_EMAIL);
        ReflectionTestUtils.setField(request, "password", UserFixture.DEFAULT_PASSWORD);
        ReflectionTestUtils.setField(request, "name", UserFixture.DEFAULT_NAME);

        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> userService.createUser(request));
        assertEquals("이미 존재하는 사용자명입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("회원가입 테스트 - 실패: 중복된 이메일 입력")
    void createUser_failure_sameEmail() {

        // Given
        UserCreateRequest request = new UserCreateRequest();
        ReflectionTestUtils.setField(request, "username", UserFixture.DEFAULT_USERNAME);
        ReflectionTestUtils.setField(request, "email", UserFixture.DEFAULT_EMAIL);
        ReflectionTestUtils.setField(request, "password", UserFixture.DEFAULT_PASSWORD);
        ReflectionTestUtils.setField(request, "name", UserFixture.DEFAULT_NAME);

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsUserByEmail(anyString())).thenReturn(true);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> userService.createUser(request));
        assertEquals("이미 사용 중인 이메일입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("사용자 정보 조회 테스트 - 성공: 유효한 사용자 ID 입력")
    void getUser_success() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        when(userRepository.findUserById(anyLong())).thenReturn(user);

        // When
        UserGetProfileResponse response = userService.getUser(1L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("사용자 목록 조회 테스트 - 성공")
    void getUserList_success() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(userRepository.findAll()).thenReturn(userList);

        // When
        List<UserListInquiryResponse> response = userService.getUserList();

        // Then
        assertThat(response).isNotNull();
        assertThat(response).hasSize(1);
        assertThat(response.get(0).getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("사용자 정보 수정 테스트 - 성공: 이름 및 이메일 변경")
    void updateUserInfo_success() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        UserUpdateInfoRequest request = new UserUpdateInfoRequest();
        ReflectionTestUtils.setField(request, "email", "changedEmail@test.com");
        ReflectionTestUtils.setField(request, "password", UserFixture.DEFAULT_PASSWORD);
        ReflectionTestUtils.setField(request, "name", "이름 변경");

        when(userRepository.findUserById(anyLong())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);

        // When
        UserUpdateInfoResponse response = userService.updateUserInfo(1L, request);

        // Then
        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("사용자 정보 수정 테스트 - 실패: 잘못된 비밀번호")
    void updateUserInfo_failure_wrongPassword() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        UserUpdateInfoRequest request = new UserUpdateInfoRequest();
        ReflectionTestUtils.setField(request, "email", UserFixture.DEFAULT_EMAIL);
        ReflectionTestUtils.setField(request, "password", "1234");
        ReflectionTestUtils.setField(request, "name", "이름 변경");

        when(userRepository.findUserById(anyLong())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(false);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> userService.updateUserInfo(1L, request));
        assertEquals("비밀번호가 올바르지 않습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("추가 가능한 사용자 조회 테스트 - 성공: 팀 ID 주어질 때")
    void findAvailableUsers_success_byTeamId() {
        // TODO: 팀별 조회 테스트
    }
}