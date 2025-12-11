package com.example.taskflow.domain.comment.controller;

import com.example.taskflow.common.model.enums.SuccessMessage;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.domain.comment.model.request.*;
import com.example.taskflow.domain.comment.model.response.*;
import com.example.taskflow.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<GlobalResponse<CommentCreateResponse>> createCommentApi(
            @AuthenticationPrincipal User user,
            @PathVariable long taskId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        CommentCreateResponse result = commentService.createComment(user.getUsername(), taskId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(GlobalResponse.success(SuccessMessage.COMMENT_CREATE_SUCCESS, result));
    }

    // 댓글 목록 조회
    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<GlobalResponse<PageResponse<CommentGetResponse>>> getCommentListApi(
            @PathVariable long taskId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "newest") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        if ("oldest".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        }

        PageResponse<CommentGetResponse> result = commentService.getCommentList(taskId, pageable);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.COMMENT_GET_LIST_SUCCESS, result));
    }

    // 댓글 수정
    @PutMapping("/tasks/{taskId}/comments/{commentId}")
    public ResponseEntity<GlobalResponse<CommentUpdateResponse>> updateCommentApi(
            @PathVariable long taskId,
            @PathVariable long commentId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CommentUpdateRequest request
    ) {
        CommentUpdateResponse result = commentService.updateComment(taskId, commentId, user.getUsername(), request);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.COMMENT_UPDATE_SUCCESS, result));
    }

    // 댓글 삭제
        @DeleteMapping("/tasks/{taskId}/comments/{commentId}")
    public ResponseEntity<GlobalResponse<Void>> deleteCommentApi(
            @PathVariable long taskId,
            @PathVariable long commentId,
            @AuthenticationPrincipal User user
    ) {
        commentService.deleteComment(taskId, commentId, user.getUsername());

        return ResponseEntity.ok(GlobalResponse.successNodata(SuccessMessage.COMMENT_DELETE_SUCCESS));
    }
}
