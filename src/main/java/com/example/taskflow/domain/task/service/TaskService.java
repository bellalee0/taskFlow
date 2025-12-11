package com.example.taskflow.domain.task.service;

import static com.example.taskflow.common.exception.ErrorMessage.*;

import com.example.taskflow.common.entity.BaseEntity;
import com.example.taskflow.common.entity.Comment;
import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.exception.ErrorMessage;
import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.domain.comment.repository.CommentRepository;
import com.example.taskflow.domain.task.model.dto.TaskDto;
import com.example.taskflow.domain.task.model.request.*;
import com.example.taskflow.domain.task.model.response.*;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.model.dto.UserDto;
import com.example.taskflow.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    // 작업 생성 기능
    @Transactional
    public TaskCreateResponse createTask(TaskCreateRequest request) {

        User assignee = findUserById(request.getAssigneeId());
        if (assignee.isDeleted()) {
            throw new CustomException(ASSIGNEE_NOT_FOUND);
        }

        Task task = new Task(request.getTitle(), request.getDescription(), request.getPriority(), assignee, request.getDueDate());
        taskRepository.save(task);

        return TaskCreateResponse.from(TaskDto.from(task), UserDto.from(task.getAssigneeId()));
    }

    // 작업 목록 조회 기능(페이징, 필터링)
    @Transactional(readOnly = true)
    public PageResponse<TaskGetAllResponse> getTaskList(
            TaskStatus status,
            TaskPriority priority,
            Long assigneeId,
            Pageable pageable) {

        Page<Task> tasks = taskRepository.findByFilters(status, priority, assigneeId, pageable);

        Page<TaskGetAllResponse> responsePage = tasks.map(task -> TaskGetAllResponse.from(TaskDto.from(task), UserDto.from(task.getAssigneeId())));

        return PageResponse.from(responsePage);
    }

    // 작업 상세 조회 기능
    @Transactional(readOnly = true)
    public TaskGetOneResponse getTaskById(long taskId) {

        Task task = taskRepository.findTaskById(taskId);
        if (task.isDeleted()) {
            throw new CustomException(TASK_NOT_FOUND);
        }

        return TaskGetOneResponse.from(TaskDto.from(task), UserDto.from(task.getAssigneeId()));
    }

    // 작업 수정 기능
    @Transactional
    public TaskUpdateResponse updateTask(long taskId, TaskUpdateRequest request) {
        Task task = taskRepository.findTaskById(taskId);
        if (task.isDeleted()) {
            throw new CustomException(TASK_NOT_FOUND);
        }

        User assignee = findUserById(request.getAssigneeId() != null
                ? findUserById(request.getAssigneeId()).getId()
                : null);

        task.update(
                request.getTitle(),
                request.getDescription(),
                assignee,
                request.getPriority(),
                request.getDueDate()
        );

        return TaskUpdateResponse.from(
                TaskDto.from(task),
                task.getAssigneeId() != null ? UserDto.from(task.getAssigneeId()) : null
        );

    }

    // 작업 상태 변경 기능
    @Transactional
    public TaskUpdateStatusResponse updateStatus(long taskId, TaskUpdateStatusRequest request) {

        Task task = taskRepository.findTaskById(taskId);
        if (task.isDeleted()) {
            throw new CustomException(TASK_NOT_FOUND);
        }

        TaskStatus currentStatus = task.getStatus();
        TaskStatus requestStatus = request.getStatus();

        if (Math.abs(requestStatus.getLevel() - currentStatus.getLevel()) > 1) {
            throw new CustomException(TASK_WRONG_ENUM);
        }

        task.updateStatus(requestStatus);
        taskRepository.saveAndFlush(task);

        return TaskUpdateStatusResponse.from(TaskDto.from(task), UserDto.from(task.getAssigneeId()));
    }

    // 작업 삭제 기능
    @Transactional
    public void deleteTask(long taskId) {
        Task task = taskRepository.findTaskById(taskId);
        if (task.isDeleted()) {
            throw new  CustomException(TASK_NOT_FOUND);
        }

        List<Comment> commentList = commentRepository.findByTaskId(task.getId());
        commentList.forEach(BaseEntity::updateIsDeleted);

        task.updateIsDeleted();
    }

    // 사용자 ID로 조회
    // TODO: 추후에 UserRepository의 default 메서드로 변경
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.ASSIGNEE_NOT_FOUND));
    }
}
