package com.example.taskflow.domain.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taskflow.common.exception.ValidationMessage;
import com.example.taskflow.common.filter.JwtFilter;
import com.example.taskflow.common.model.enums.UserRole;
import com.example.taskflow.domain.user.model.request.UserCreateRequest;
import com.example.taskflow.domain.user.model.request.UserUpdateInfoRequest;
import com.example.taskflow.domain.user.model.response.UserAvailableTeamResponse;
import com.example.taskflow.domain.user.model.response.UserCreateResponse;
import com.example.taskflow.domain.user.model.response.UserGetProfileResponse;
import com.example.taskflow.domain.user.model.response.UserListInquiryResponse;
import com.example.taskflow.domain.user.model.response.UserUpdateInfoResponse;
import com.example.taskflow.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = UserController.class,
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = JwtFilter.class
        )
    }
)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("POST /api/users 회원가입 테스트")
    void createUserApi_success() throws Exception {

        // given
        UserCreateRequest request = new UserCreateRequest();
        ReflectionTestUtils.setField(request, "username", "test");
        ReflectionTestUtils.setField(request, "email", "test@test.com");
        ReflectionTestUtils.setField(request, "password", "qwer1234!");
        ReflectionTestUtils.setField(request, "name", "test");

        UserCreateResponse expectedResponse = new UserCreateResponse(
            1L, "test", "test@test.com", "test", "USER", LocalDateTime.now()
        );

        given(userService.createUser(request)).willReturn(expectedResponse);

        // when & then
        mockMvc.perform(post("/api/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /api/users 회원가입 테스트 - 실패: 필수값 입력 X")
    void createUserApi_failure_validationBlank() throws Exception {

        // given
        UserCreateRequest request = new UserCreateRequest();
        ReflectionTestUtils.setField(request, "username", "");
        ReflectionTestUtils.setField(request, "email", "test@test.com");
        ReflectionTestUtils.setField(request, "password", "qwer1234!");
        ReflectionTestUtils.setField(request, "name", "test");

        // when & then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ValidationMessage.USERNAME_NOT_BLANK));
    }

    @Test
    @DisplayName("POST /api/users 회원가입 테스트 - 실패: 이메일 형식 X")
    void createUserApi_failure_validationNotEmail() throws Exception {

        // given
        UserCreateRequest request = new UserCreateRequest();
        ReflectionTestUtils.setField(request, "username", "test");
        ReflectionTestUtils.setField(request, "email", "test");
        ReflectionTestUtils.setField(request, "password", "qwer1234!");
        ReflectionTestUtils.setField(request, "name", "test");

        // when & then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ValidationMessage.EMAIL_FORMAT));
    }

    @Test
    @DisplayName("POST /api/users 회원가입 테스트 - 실패: 비밀번호 형식 X")
    void createUserApi_failure_validationUnmatchPasswordFormat() throws Exception {

        // given
        UserCreateRequest request = new UserCreateRequest();
        ReflectionTestUtils.setField(request, "username", "test");
        ReflectionTestUtils.setField(request, "email", "test");
        ReflectionTestUtils.setField(request, "password", "1234");
        ReflectionTestUtils.setField(request, "name", "test");

        // when & then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ValidationMessage.PASSWORD_PATTERN_MESSAGE));
    }

    @Test
    @DisplayName("GET /api/users/{id} 사용자 정보 조회 테스트")
    void getUserApi_success() throws Exception {

        // given
        long userId = 1L;
        UserGetProfileResponse expectedResponse = new UserGetProfileResponse(
            1L, "test", "test@test.com", "test", "USER", LocalDateTime.now(), LocalDateTime.now()
        );

        given(userService.getUser(anyLong())).willReturn(expectedResponse);

        // when & then
        mockMvc.perform(get("/api/users/{id}", userId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(userId))
            .andExpect(jsonPath("$.data.username").value("test"))
            .andExpect(jsonPath("$.data.email").value("test@test.com"));
    }

    @Test
    @DisplayName("GET /api/users 사용자 목록 조회 테스트")
    void getUserListApi_success() throws Exception {

        // given
        UserListInquiryResponse user1 = new UserListInquiryResponse(
            1L, "test", "test@test.com", "test", "USER", LocalDateTime.now()
        );
        UserListInquiryResponse user2 = new UserListInquiryResponse(
            2L, "test2", "test2@test.com", "test2", "USER", LocalDateTime.now()
        );

        given(userService.getUserList()).willReturn(List.of(user1, user2));

        // when & then
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].username").value("test"))
            .andExpect(jsonPath("$.data[1].username").value("test2"));
    }

    @Test
    @DisplayName("PUT /api/users/{id} 사용자 정보 수정 테스트")
    void updateUserInfo_success() throws Exception {

        // given
        long userId = 1L;

        UserUpdateInfoRequest request = new UserUpdateInfoRequest();
        ReflectionTestUtils.setField(request, "email", "change@test.com");
        ReflectionTestUtils.setField(request, "password", "qwer1234!");
        ReflectionTestUtils.setField(request, "name", "changeName");

        UserUpdateInfoResponse expectedResponse = new UserUpdateInfoResponse(
            1L, "test", "change@test.com", "changeName", UserRole.USER, LocalDateTime.now().minusDays(1), LocalDateTime.now()
        );

        given(userService.updateUserInfo(anyLong(), any(UserUpdateInfoRequest.class))).willReturn(expectedResponse);

        // when & then
        mockMvc.perform(put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.email").value("change@test.com"))
            .andExpect(jsonPath("$.data.name").value("changeName"));
    }

    @Test
    @DisplayName("DELETE /api/users 회원 탈퇴 테스트")
    void deleteUserApi_success() throws Exception {

        // given
        long userId = 1L;

        willDoNothing().given(userService).deleteUser(anyLong());

        // when & then
        mockMvc.perform(delete("/api/users/{id}", userId))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/users/available 추가 가능한 사용자 조회 테스트")
    void findTeamUser_success() throws Exception {

        // given
        UserAvailableTeamResponse expectedResponse = new UserAvailableTeamResponse(
            1L, "test", "test@test.com", "test", "USER", LocalDateTime.now()
        );

        given(userService.findAvailableUsers(null)).willReturn(List.of(expectedResponse));

        // when & then
        mockMvc.perform(get("/api/users/available"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].username").value("test"));
    }
}