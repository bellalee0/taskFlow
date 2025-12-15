package com.example.taskflow.domain.auth.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taskflow.common.filter.JwtFilter;
import com.example.taskflow.domain.auth.model.request.AuthLoginRequest;
import com.example.taskflow.domain.auth.model.request.AuthVerifyPasswordRequest;
import com.example.taskflow.domain.auth.model.response.AuthLoginResponse;
import com.example.taskflow.domain.auth.model.response.AuthVerifyPasswordResponse;
import com.example.taskflow.domain.auth.service.AuthenticationService;
import com.example.taskflow.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = AuthenticationController.class,
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = JwtFilter.class
        )
    }
)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthenticationService authenticationService;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("POST /api/auth/login 로그인 테스트")
    void loginApi_success() throws Exception {

        // given
        AuthLoginRequest request = new AuthLoginRequest();
        ReflectionTestUtils.setField(request, "username", "test");
        ReflectionTestUtils.setField(request, "password", "qwer1234!");

        AuthLoginResponse expectedResponse = new AuthLoginResponse(
            "Bearer token"
        );

        given(authenticationService.userLogin(any(AuthLoginRequest.class))).willReturn(expectedResponse);

        // when & then
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.token").value("Bearer token"));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/users/verify-password 비밀번호 확인 테스트")
    void verifyPasswordApi() throws Exception {

        // given
        AuthVerifyPasswordRequest request = new AuthVerifyPasswordRequest();
        ReflectionTestUtils.setField(request, "password", "qwer1234!");

        AuthVerifyPasswordResponse expectedResponse = new AuthVerifyPasswordResponse(
            true
        );

        given(authenticationService.verifyPassword(anyString(), any(AuthVerifyPasswordRequest.class))).willReturn(expectedResponse);

        // when & then
        mockMvc.perform(post("/api/users/verify-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }
}