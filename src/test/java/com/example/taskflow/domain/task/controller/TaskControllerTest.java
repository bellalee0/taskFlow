package com.example.taskflow.domain.task.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taskflow.common.filter.JwtFilter;
import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.domain.auth.model.request.AuthLoginRequest;
import com.example.taskflow.domain.auth.model.response.AuthLoginResponse;
import com.example.taskflow.domain.task.model.request.TaskCreateRequest;
import com.example.taskflow.domain.task.model.request.TaskUpdateRequest;
import com.example.taskflow.domain.task.model.request.TaskUpdateStatusRequest;
import com.example.taskflow.domain.task.model.response.TaskAssgineeResponse;
import com.example.taskflow.domain.task.model.response.TaskCreateResponse;
import com.example.taskflow.domain.task.model.response.TaskGetAllResponse;
import com.example.taskflow.domain.task.model.response.TaskGetOneResponse;
import com.example.taskflow.domain.task.model.response.TaskUpdateResponse;
import com.example.taskflow.domain.task.model.response.TaskUpdateStatusResponse;
import com.example.taskflow.domain.task.service.TaskService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = TaskController.class,
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = JwtFilter.class
        )
    }
)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    @Test
    @DisplayName("POST /api/tasks 작업 생성 테스트")
    void createTaskApi_success() throws Exception {

        // given
        TaskCreateRequest request = new TaskCreateRequest();
        ReflectionTestUtils.setField(request, "title", "작업 생성 테스트");
        ReflectionTestUtils.setField(request, "description", "작업애 대한 설명");
        ReflectionTestUtils.setField(request, "priority", TaskPriority.HIGH);
        ReflectionTestUtils.setField(request, "assigneeId", 1L);
        ReflectionTestUtils.setField(request, "dueDate", LocalDateTime.now().plusDays(1));

        TaskCreateResponse expectedResponse = new TaskCreateResponse(
            1L, "작업 생성 테스트", "작업에 대한 설명", TaskStatus.TODO, TaskPriority.HIGH, 1L,
            new TaskAssgineeResponse(1L, "test", "test"),
            LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now().plusDays(1)
        );

        given(taskService.createTask(any(TaskCreateRequest.class))).willReturn(expectedResponse);

        // when & then
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.title").value("작업 생성 테스트"));
    }

    @Test
    @DisplayName("GET /api/tasks 작업 목록 조회 테스트")
    void getTaskListApi_success() throws Exception {

        // given
        Pageable pageable = PageRequest.of(0, 10);

        TaskGetAllResponse task = new TaskGetAllResponse(
            1L, "작업1", "작업에 대한 설명", TaskStatus.TODO, TaskPriority.HIGH, 1L,
            new TaskAssgineeResponse(1L, "test", "test"),
            LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now().plusDays(1)
        );

        Page<TaskGetAllResponse> taskPage = new PageImpl<>(List.of(task), pageable, 1);
        PageResponse<TaskGetAllResponse> expectedResponse = PageResponse.from(taskPage);

        given(taskService.getTaskList(null, null, null, pageable)).willReturn(expectedResponse);

        // when & then
        mockMvc.perform(get("/api/tasks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.content[0].id").value(1L))
            .andExpect(jsonPath("$.data.content[0].title").value("작업1"));
    }

    @Test
    @DisplayName("GET /api/tasks/{id} 작업 상세 조회 테스트")
    void getTaskApi_success() throws Exception {

        // given
        long id = 1L;

        TaskGetOneResponse expectedResponse = new TaskGetOneResponse(
            1L, "작업1", "작업에 대한 설명", TaskStatus.TODO, TaskPriority.HIGH, 1L,
            new TaskAssgineeResponse(1L, "test", "test"),
            LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now().plusDays(1)
        );

        given(taskService.getTaskById(anyLong())).willReturn(expectedResponse);

        // when & then
        mockMvc.perform(get("/api/tasks/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.title").value("작업1"));
    }

    @Test
    @DisplayName("PUT /api/tasks/{id} 작업 수정 테스트")
    void updateTaskApi_success() throws Exception {

        // given
        long id = 1L;
        TaskUpdateRequest request = new TaskUpdateRequest();
        ReflectionTestUtils.setField(request, "title", "작업 제목 변경");
        ReflectionTestUtils.setField(request, "description", "설명 수정");
        ReflectionTestUtils.setField(request, "status", TaskStatus.IN_PROGRESS);
        ReflectionTestUtils.setField(request, "priority", TaskPriority.MEDIUM);
        ReflectionTestUtils.setField(request, "assigneeId", 1L);
        ReflectionTestUtils.setField(request, "dueDate", LocalDateTime.now().plusDays(2));

        TaskUpdateResponse expectedResponse = new TaskUpdateResponse(
            1L, "작업 제목 변경", "설명 수정", TaskStatus.IN_PROGRESS, TaskPriority.MEDIUM, 1L,
            new TaskAssgineeResponse(1L, "test", "test"),
            LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now().plusDays(2)
        );

        given(taskService.updateTask(anyLong(), any(TaskUpdateRequest.class))).willReturn(expectedResponse);

        // when & then
        mockMvc.perform(put("/api/tasks/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.title").value("작업 제목 변경"));
    }

    @Test
    @DisplayName("PATCH /api/tasks/{id} 작업 상태 변경 테스트")
    void updateStatusApi_success() throws Exception {

        // given
        long id = 1L;
        TaskUpdateStatusRequest request = new TaskUpdateStatusRequest();
        ReflectionTestUtils.setField(request, "status", TaskStatus.DONE);

        TaskUpdateStatusResponse expectedResponse = new TaskUpdateStatusResponse(
            1L, "작업1", "설명", TaskStatus.DONE.toString(), TaskPriority.MEDIUM.toString(), 1L,
            new TaskAssgineeResponse(1L, "test", "test"),
            LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now().plusDays(2)
        );

        given(taskService.updateStatus(anyLong(), any(TaskUpdateStatusRequest.class))).willReturn(expectedResponse);

        // when & then
        mockMvc.perform(patch("/api/tasks/{id}/status", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.status").value("DONE"));
    }

    @Test
    @DisplayName("PATCH /api/tasks/{id} 작업 상태 변경 테스트")
    void deleteTaskApi_success() throws Exception {

        // given
        long id = 1L;

        willDoNothing().given(taskService).deleteTask(anyLong());

        // when & then
        mockMvc.perform(delete("/api/tasks/{id}", id))
            .andExpect(status().isOk());
    }
}