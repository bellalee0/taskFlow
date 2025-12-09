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
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalStateException("User Not Found"));

        Comment parentComment = null;

        if (request.getParentId() != null) {
            parentComment = commentRepository.findCommentById(request.getParentId());
            // TODO: 이때 발생해야 하는 예외는 404보다는 400인 것 같은데, API 명세서에는 400에 대한 내용이 없음
            if (parentComment.getParentComment() != null) { throw new CustomException(COMMENT_NOT_FOUND); }
        }

        Comment comment = new Comment(request.getContent(), user, task, parentComment);

        commentRepository.save(comment);

        // TODO: UserDto 요구사항에 맞게 변경 필요(id, username, name)
        return CommentCreateResponse.from(CommentDto.from(comment), UserDto.from(user));
    }

    @Transactional(readOnly = true)
    public PageResponse<CommentGetResponse> getCommentList(long taskId, Pageable pageable) {

        Page<Comment> commentList = commentRepository.findByTaskId(taskId, pageable);

        Page<CommentGetResponse> responsePage = commentList
            // TODO: UserDto 요구사항에 맞게 변경 필요(id, username, name, email, role)
            .map(comment -> CommentGetResponse.from(CommentDto.from(comment), UserDto.from(comment.getUser())));

        return PageResponse.from(responsePage);
    }

    public CommentUpdateResponse updateComment(long taskId, long commentId, long userId, CommentUpdateRequest request) {

        // TODO: task를 만들어야 할까?

        Comment comment = commentRepository.findCommentById(commentId);

        if (!Objects.equals(comment.getUser().getId(), userId)) {
            throw new CustomException(COMMENT_NO_PERMISSION);
        }

        comment.update(request);
        commentRepository.saveAndFlush(comment);

        return CommentUpdateResponse.from(CommentDto.from(comment));
    }

    public void deleteComment(long taskId, long commentId, long userId) {

        // TODO: task를 만들어야 할까?

        Comment comment = commentRepository.findCommentById(commentId);

        if (!Objects.equals(comment.getUser().getId(), userId)) { throw new CustomException(COMMENT_NO_PERMISSION); }

        if (comment.isDeleted()) { throw new CustomException(COMMENT_NOT_FOUND_COMMENT); }

        if (commentRepository.existsByParentCommentId(comment.getId())) {
            List<Comment> childCommentList = commentRepository.findAllByParentCommentId(comment.getId());
            childCommentList.forEach(BaseEntity::updateIsDeleted);
        }

        comment.updateIsDeleted();
    }
}
