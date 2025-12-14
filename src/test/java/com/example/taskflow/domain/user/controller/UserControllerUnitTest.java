package com.example.taskflow.domain.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taskflow.common.exception.GlobalExceptionHandler;
import com.example.taskflow.common.filter.JwtFilter;
import com.example.taskflow.domain.user.model.response.UserCreateResponse;
import com.example.taskflow.domain.user.model.response.UserGetProfileResponse;
import com.example.taskflow.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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
class UserControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void 특정_사용자_조회가_정상적으로_처리된다() throws Exception {

        // given
        long userId = 1L;
        UserGetProfileResponse expectedResponse = new UserGetProfileResponse(
            1L, "test", "test@test.com", "test", "USER",
            LocalDateTime.now(), LocalDateTime.now()
        );

        given(userService.getUser(anyLong())).willReturn(expectedResponse);

        // when & then
        mockMvc.perform(get("/api/users/{userId}", userId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(userId))
            .andExpect(jsonPath("$.email").value("test@test.com"));
    }
}