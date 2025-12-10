package com.example.taskflow.domain.task.controller;

import com.example.taskflow.common.model.enums.SuccessMessage;
import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.domain.task.model.request.*;
import com.example.taskflow.domain.task.model.response.*;
import com.example.taskflow.domain.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // 작업 생성 기능
    @PostMapping
    public ResponseEntity<GlobalResponse<TaskCreateResponse>> createTaskApi(
            @Valid @RequestBody TaskCreateRequest request
    ) {
        TaskCreateResponse response = taskService.createTask(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(GlobalResponse.success(SuccessMessage.TASK_CREATE_SUCCESS, response));
    }

    // 작업 목록 조회 기능 (페이징, 필터링)
    @GetMapping
    public ResponseEntity<GlobalResponse<PageResponse<TaskGetAllResponse>>> getTaskListApi(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) Long assigneeId,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
       PageResponse<TaskGetAllResponse> response = taskService.getTaskList(status, priority, assigneeId, pageable);

        return  ResponseEntity.ok(GlobalResponse.success(SuccessMessage.TASK_GET_LIST_SUCCESS, response));
    }

    // 작업 상세 조회 기능
    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<TaskGetOneResponse>> getTaskApi(
            @PathVariable Long id
    ) {
        TaskGetOneResponse response = taskService.getTaskById(id);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.TASK_GET_ONE_SUCCESS, response));
    }

    // 작업 수정 기능
    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse<TaskUpdateResponse>> updateTaskApi(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest request
    ) {
        TaskUpdateResponse response = taskService.updateTask(id, request);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.TASK_UPDATE_SUCCESS, response));
    }

    // 작업 상태 변경 기능
    @PatchMapping("/{id}/status")
    public ResponseEntity<GlobalResponse<TaskUpdateStatusResponse>> updateStatusApi(
        @PathVariable long id,
        @Valid @RequestBody TaskUpdateStatusRequest request
    ) {
        TaskUpdateStatusResponse result = taskService.updateStatus(id, request);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.TASK_UPDATE_STATUS_SUCCESS, result));
    }

    // 작업 삭제 기능
//    @DeleteMapping("/{id}")
    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<GlobalResponse<Void>> deleteTaskApi(
            @PathVariable long id,
            @PathVariable long userId
    ) {
        taskService.deleteTask(id, userId);

        return ResponseEntity.ok(GlobalResponse.successNodata(SuccessMessage.TASK_DELETE_SUCCESS));
    }
}
