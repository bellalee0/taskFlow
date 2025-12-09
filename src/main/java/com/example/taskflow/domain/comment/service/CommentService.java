package com.example.taskflow.domain.comment.service;

import static com.example.taskflow.common.exception.ErrorMessage.*;

import com.example.taskflow.common.entity.BaseEntity;
import com.example.taskflow.common.entity.Comment;
import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.domain.comment.model.dto.CommentDto;
import com.example.taskflow.domain.comment.model.request.CommentCreateRequest;
import com.example.taskflow.domain.comment.model.request.CommentUpdateRequest;
import com.example.taskflow.domain.comment.model.response.CommentCreateResponse;
import com.example.taskflow.domain.comment.model.response.CommentGetResponse;
import com.example.taskflow.domain.comment.model.response.CommentUpdateResponse;
import com.example.taskflow.domain.comment.repository.CommentRepository;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.model.dto.UserDto;
import com.example.taskflow.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public CommentCreateResponse createComment(long userId, long taskId, CommentCreateRequest request) {

        // TODO: default 메서드로 변경
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User Not Found"));

        // TODO: default 메서드로 변경
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalStateException("Task Not Found"));

        Comment parentComment = null;

        if (request.getParentId() != null) {
            parentComment = commentRepository.findCommentById(request.getParentId(), COMMENT_NOT_FOUND_TASK_OR_COMMENT);

            checkTaskCommentRelationship(task, parentComment);
            if (parentComment.getParentComment() != null) { throw new CustomException(COMMENT_NOT_FOUND_TASK_OR_COMMENT); }
        }

        Comment comment = new Comment(request.getContent(), user, task, parentComment);
        commentRepository.save(comment);

        // TODO: UserDto 요구사항에 맞게 변경 필요(id, username, name)
        return CommentCreateResponse.from(CommentDto.from(comment), UserDto.from(user));
    }

    @Transactional(readOnly = true)
    public PageResponse<CommentGetResponse> getCommentList(long taskId, Pageable pageable) {

        // TODO: Task default 메서드로 변경
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalStateException("Task Not Found"));

        Page<Comment> commentList = commentRepository.findByTaskId(task.getId(), pageable);

        Page<CommentGetResponse> responsePage = commentList
            // TODO: UserDto 요구사항에 맞게 변경 필요(id, username, name, email, role)
            .map(comment -> CommentGetResponse.from(CommentDto.from(comment), UserDto.from(comment.getUser())));

        return PageResponse.from(responsePage);
    }

    public CommentUpdateResponse updateComment(long taskId, long commentId, long userId, CommentUpdateRequest request) {

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalStateException("Task Not Found"));
        Comment comment = commentRepository.findCommentById(commentId, COMMENT_NOT_FOUND_COMMENT);
        checkTaskCommentRelationship(task, comment);

        checkCommentUserRelationship(userId, comment);

        comment.update(request);
        commentRepository.saveAndFlush(comment);

        return CommentUpdateResponse.from(CommentDto.from(comment));
    }

    public void deleteComment(long taskId, long commentId, long userId) {

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalStateException("Task Not Found"));
        Comment comment = commentRepository.findCommentById(commentId, COMMENT_NOT_FOUND_COMMENT);
        checkTaskCommentRelationship(task, comment);

        checkCommentUserRelationship(userId, comment);

        if (comment.isDeleted()) { throw new CustomException(COMMENT_NO_PERMISSION_DELETE); }

        if (commentRepository.existsByParentCommentId(comment.getId())) {
            List<Comment> childCommentList = commentRepository.findAllByParentCommentId(comment.getId());
            childCommentList.forEach(BaseEntity::updateIsDeleted);
        }

        comment.updateIsDeleted();
    }

    // Comment가 Task의 댓글인지 확인
    private static void checkTaskCommentRelationship(Task task, Comment parentComment) {
        if (!Objects.equals(task.getId(), parentComment.getTask().getId())) {
            throw new CustomException(COMMENT_NOT_FOUND_TASK_OR_COMMENT);
        }
    }

    // User가 Comment의 작성자인지 확인
    private static void checkCommentUserRelationship(long userId, Comment comment) {
        if (!Objects.equals(comment.getUser().getId(), userId)) {
            throw new CustomException(COMMENT_NO_PERMISSION_UPDATE);
        }
    }
}
