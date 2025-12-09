package com.example.taskflow.domain.comment.controller;

import com.example.taskflow.common.model.enums.SuccessMessage;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.comment.model.request.CommentCreateRequest;
import com.example.taskflow.domain.comment.model.response.CommentCreateResponse;
import com.example.taskflow.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

//    @PostMapping("/tasks/{taskId}/comments")
    @PostMapping("/tasks/{taskId}/comments/{userId}")
    public ResponseEntity<GlobalResponse<CommentCreateResponse>> createComment(
            // TODO: 로그인 사용자 정보로 변경
            @PathVariable long userId,
            @PathVariable long taskId,
            @Valid @RequestBody CommentCreateRequest request) {

        CommentCreateResponse result = commentService.createComment(userId, taskId, request);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.COMMENT_CREATE_SUCCESS, result));
    }
}
