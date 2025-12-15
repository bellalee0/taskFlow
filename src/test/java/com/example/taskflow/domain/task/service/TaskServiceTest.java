package com.example.taskflow.domain.task.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.domain.comment.repository.CommentRepository;
import com.example.taskflow.domain.task.model.request.TaskCreateRequest;
import com.example.taskflow.domain.task.model.request.TaskUpdateRequest;
import com.example.taskflow.domain.task.model.request.TaskUpdateStatusRequest;
import com.example.taskflow.domain.task.model.response.TaskCreateResponse;
import com.example.taskflow.domain.task.model.response.TaskGetAllResponse;
import com.example.taskflow.domain.task.model.response.TaskGetOneResponse;
import com.example.taskflow.domain.task.model.response.TaskUpdateResponse;
import com.example.taskflow.domain.task.model.response.TaskUpdateStatusResponse;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import com.example.taskflow.fixture.TaskFixture;
import com.example.taskflow.fixture.UserFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("작업 생성 테스트 - 성공: 유효한 내용 입력")
    void createTask_success() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        TaskCreateRequest request = new TaskCreateRequest();
        ReflectionTestUtils.setField(request, "title", TaskFixture.DEFAULT_TITLE);
        ReflectionTestUtils.setField(request, "description", TaskFixture.DEFAULT_DESCRIPTION);
        ReflectionTestUtils.setField(request, "priority", TaskFixture.DEFAULT_PRIORITY);
        ReflectionTestUtils.setField(request, "assigneeId", 1L);
        ReflectionTestUtils.setField(request, "dueDate", TaskFixture.DEFAULT_DUE_DATE);

        when(userRepository.findUserById(anyLong())).thenReturn(user);

        // When
        TaskCreateResponse response = taskService.createTask(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo(TaskFixture.DEFAULT_TITLE);
        assertThat(response.getAssignee().getUsername()).isEqualTo(user.getUsername());

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("작업 목록 조회 테스트 - 성공")
    void getTaskList_success() {

        // Given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        Page<Task> todoPage = new PageImpl<>(List.of(task), pageable, 1);

        when(taskRepository.findByFilters(null, null, null, pageable)).thenReturn(todoPage);

        // When
        PageResponse<TaskGetAllResponse> response = taskService.getTaskList(null, null, null, pageable);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getContent().get(0).getTitle()).isEqualTo(task.getTitle());
        assertThat(response.getContent().get(0).getAssignee().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("작업 상세 조회 테스트 - 성공: 유효한 작업 ID 입력")
    void getTaskById_success() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);

        // When
        TaskGetOneResponse response = taskService.getTaskById(1L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo(task.getTitle());
        assertThat(response.getAssignee().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("작업 수정 테스트 - 성공: 유효한 내용 입력")
    void updateTask_success() {

        // Given
        User user1 = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user1, "id", 1L);

        User user2 = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user2, "id", 2L);

        Task task = TaskFixture.createTestTask(user1);
        ReflectionTestUtils.setField(task, "id", 1L);

        TaskUpdateRequest request = new TaskUpdateRequest();
        ReflectionTestUtils.setField(request, "title", "작업 제목 변경");
        ReflectionTestUtils.setField(request, "description", "작업 설명 변경");
        ReflectionTestUtils.setField(request, "status", TaskStatus.IN_PROGRESS);
        ReflectionTestUtils.setField(request, "priority", TaskPriority.MEDIUM);
        ReflectionTestUtils.setField(request, "assigneeId", 2L);
        ReflectionTestUtils.setField(request, "dueDate", TaskFixture.DEFAULT_DUE_DATE);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);
        when(userRepository.findUserById(anyLong())).thenReturn(user2);

        // When
        TaskUpdateResponse response = taskService.updateTask(1L, request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("작업 제목 변경");
        assertThat(response.getDescription()).isEqualTo("작업 설명 변경");
        assertThat(response.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        assertThat(response.getPriority()).isEqualTo(TaskPriority.MEDIUM);
        assertThat(response.getAssignee().getUsername()).isEqualTo(user2.getUsername());
    }

    @Test
    @DisplayName("작업 수정 테스트 - 성공: Request의 Assignee가 null인 경우")
    void updateTask_success_noAssignee() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        TaskUpdateRequest request = new TaskUpdateRequest();
        ReflectionTestUtils.setField(request, "title", "작업 제목 변경");
        ReflectionTestUtils.setField(request, "description", "작업 설명 변경");
        ReflectionTestUtils.setField(request, "status", TaskStatus.IN_PROGRESS);
        ReflectionTestUtils.setField(request, "priority", TaskPriority.MEDIUM);
        ReflectionTestUtils.setField(request, "assigneeId", null);
        ReflectionTestUtils.setField(request, "dueDate", TaskFixture.DEFAULT_DUE_DATE);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);

        // When
        TaskUpdateResponse response = taskService.updateTask(1L, request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("작업 제목 변경");
        assertThat(response.getDescription()).isEqualTo("작업 설명 변경");
        assertThat(response.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        assertThat(response.getPriority()).isEqualTo(TaskPriority.MEDIUM);
        assertThat(response.getAssignee().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("작업 상태 변경 테스트 - 성공: 유효한 상태 입력")
    void updateStatus_success() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        TaskUpdateStatusRequest request = new TaskUpdateStatusRequest();
        ReflectionTestUtils.setField(request, "status", TaskStatus.IN_PROGRESS);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);

        // When
        TaskUpdateStatusResponse response = taskService.updateStatus(1L, request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS.toString());
    }

    @Test
    @DisplayName("작업 상태 변경 테스트 - 실패: TODO -> DONE으로 상태 변경")
    void updateStatus_failure_changeTodoToDone() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        TaskUpdateStatusRequest request = new TaskUpdateStatusRequest();
        ReflectionTestUtils.setField(request, "status", TaskStatus.DONE);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> taskService.updateStatus(1L, request));
        assertEquals("유효하지 않은 상태 값입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("작업 상태 변경 테스트 - 성공: 작업 완료 시, 작업 완료 관련 필드 변경 확인")
    void updateStatus_success_changeStatusToDone() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);
        ReflectionTestUtils.setField(task, "status", TaskStatus.IN_PROGRESS);

        TaskUpdateStatusRequest request = new TaskUpdateStatusRequest();
        ReflectionTestUtils.setField(request, "status", TaskStatus.DONE);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);

        // When
        TaskUpdateStatusResponse response = taskService.updateStatus(1L, request);

        // Then
        assertThat(task.isCompleted()).isTrue();
        assertThat(task.getCompletedDateTime()).isNotNull();
    }

    @Test
    @DisplayName("작업 상태 변경 테스트 - 성공: 작업 완료에서 진행 중으로 변경 시, 작업 완료 관련 필드 삭제 확인")
    void updateStatus_success_changeStatusDoneToInProgress() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);
        ReflectionTestUtils.setField(task, "status", TaskStatus.DONE);

        TaskUpdateStatusRequest request = new TaskUpdateStatusRequest();
        ReflectionTestUtils.setField(request, "status", TaskStatus.IN_PROGRESS);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);

        // When
        TaskUpdateStatusResponse response = taskService.updateStatus(1L, request);

        // Then
        assertThat(task.isCompleted()).isFalse();
        assertThat(task.getCompletedDateTime()).isNull();
    }

    @Test
    @DisplayName("작업 삭제 테스트 - 성공: 유효한 내용 입력")
    void deleteTask_success() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);

        // When & Then
        taskService.deleteTask(1L);
    }

    @Test
    @DisplayName("작업 삭제 테스트 - 실패: 이미 삭제된 작업인 경우")
    void deleteTask_failure() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);
        ReflectionTestUtils.setField(task, "isDeleted", true);

        when(taskRepository.findTaskById(anyLong())).thenReturn(task);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> taskService.deleteTask(1L));
        assertEquals("작업을 찾을 수 없습니다.", exception.getMessage());
    }
}