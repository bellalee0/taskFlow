package com.example.taskflow.domain.task.service;

import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.exception.ErrorMessage;
import com.example.taskflow.common.model.enums.SuccessMessage;
import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.task.model.request.TaskCreateRequest;
import com.example.taskflow.domain.task.model.response.TaskCreateResponse;
import com.example.taskflow.domain.task.model.response.TaskGetAllResponse;
import com.example.taskflow.domain.task.model.response.TaskGetOneResponse;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // 작업 생성 기능
    public GlobalResponse<TaskCreateResponse> createTask(TaskCreateRequest request) {

        validateRequiredFields(request.getTitle(), request.getDescription());

        Long assigneeIdLong = Long.parseLong(request.getAssigneeId());
        User assignee = findUserById(assigneeIdLong);

        if (request.getTitle() == null || request.getDescription() == null) {
            throw new CustomException(ErrorMessage.TASK_REQUIRED_FIELD);
        }

        TaskPriority priority = TaskPriority.valueOf(request.getPriority().toUpperCase());
        LocalDateTime dueDateTime = parseDueDate(request.getDueDateTime());

        Task task = new Task(request.getTitle(), request.getDescription(), priority, assignee, dueDateTime);
        Task savedTask = taskRepository.save(task);

        return GlobalResponse.success(
                SuccessMessage.TASK_CREATE_SUCCESS,
                TaskCreateResponse.from(task)
        );
    }

    // 작업 목록 조회 기능(페이징, 필터링)
    @Transactional(readOnly = true)
    public GlobalResponse<Page<TaskGetAllResponse>> getTaskList(
            TaskStatus status,
            TaskPriority priority,
            Long assigneeId,
            Pageable pageable) {

        Page<Task> tasks = taskRepository.findByFilters(status, priority, assigneeId, pageable);

        Page<TaskGetAllResponse> responsePage =
                tasks.map(TaskGetAllResponse::from);

        return GlobalResponse.success(
                SuccessMessage.TASK_GET_LIST_SUCCESS,
                responsePage
        );
    }

    // 작업 상세 조회 기능
    public GlobalResponse<TaskGetOneResponse> getTaskById(Long taskId) {
        Task task = findTaskById(taskId);
        return GlobalResponse.success(
                SuccessMessage.TASK_GET_ONE_SUCCESS,
                TaskGetOneResponse.from(task)
        );
    }

    // 작업 수정 기능

    // 작업 상태 변경 기능

    // 작업 삭제 기능


    // 필수 필드 검증
    private void validateRequiredFields(String title, String description) {
        if (title == null || title.trim().isEmpty()) {
            throw new CustomException(ErrorMessage.TASK_REQUIRED_FIELD);
        }
        if (description == null || description.trim().isEmpty()) {
            throw new CustomException(ErrorMessage.TASK_REQUIRED_FIELD);
        }
    }

    // 작업 ID로 조회
    private Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(ErrorMessage.TASK_NOT_FOUND));
    }

    // 사용자 ID로 조회
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.ASSIGNEE_NOT_FOUND));
    }

    // 마감일 문자열을 LocalDateTime으로 변환
    private LocalDateTime parseDueDate(String dueDateTime) {
        if (dueDateTime == null || dueDateTime.trim().isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(dueDateTime, formatter);
    }
}
