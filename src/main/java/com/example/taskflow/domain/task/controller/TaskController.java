package com.example.taskflow.domain.task.controller;

import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.task.model.request.TaskCreateRequest;
import com.example.taskflow.domain.task.model.response.TaskCreateResponse;
import com.example.taskflow.domain.task.model.response.TaskGetAllResponse;
import com.example.taskflow.domain.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<GlobalResponse<TaskCreateResponse>> createTask(
            @Valid @RequestBody TaskCreateRequest request) {
        GlobalResponse<TaskCreateResponse> response = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 작업 목록 조회 기능 (페이징, 필터링)
    @GetMapping
    public ResponseEntity<GlobalResponse<Page<TaskGetAllResponse>>> readTaskList(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) Long assigneeId,
            Pageable pageable) {
       GlobalResponse<Page<TaskGetAllResponse>> response = taskService.getTaskList(status, priority, assigneeId, pageable);
        return  ResponseEntity.ok(response);
    }

    // 작업 상세 조회 기능

    // 작업 수정 기능

    // 작업 상태 변경 기능

    // 작업 삭제 기능

}
