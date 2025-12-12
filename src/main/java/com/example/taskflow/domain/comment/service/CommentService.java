package com.example.taskflow.domain.comment.service;

import static com.example.taskflow.common.exception.ErrorMessage.*;

import com.example.taskflow.common.entity.BaseEntity;
import com.example.taskflow.common.entity.Comment;
import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.domain.comment.model.dto.CommentDto;
import com.example.taskflow.domain.comment.model.request.*;
import com.example.taskflow.domain.comment.model.response.*;
import com.example.taskflow.domain.comment.repository.CommentRepository;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.team.model.dto.MemberInfoDto;
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
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    // 댓글 생성
    @Transactional
    public CommentCreateResponse createComment(String username, long taskId, CommentCreateRequest request) {

        User user = userRepository.findUserByUsername(username);
        Task task = taskRepository.findTaskById(taskId);

        Comment parentComment = null;

        if (request.getParentId() != null) {
            parentComment = commentRepository.findCommentById(request.getParentId(), COMMENT_NOT_FOUND_TASK_OR_COMMENT);

            checkTaskCommentRelationship(task, parentComment);
            if (parentComment.getParentComment() != null) { throw new CustomException(COMMENT_NOT_FOUND_TASK_OR_COMMENT); }
        }

        Comment comment = new Comment(request.getContent(), user, task, parentComment);
        commentRepository.save(comment);

        return CommentCreateResponse.from(CommentDto.from(comment), MemberInfoDto.from(UserDto.from(user)));
    }

    // 댓글 목록 조회
    @Transactional(readOnly = true)
    public PageResponse<CommentGetResponse> getCommentList(long taskId, Pageable pageable) {

        Task task = taskRepository.findTaskById(taskId);

        Page<Comment> commentList = commentRepository.findByTaskId(task.getId(), pageable);

        Page<CommentGetResponse> responsePage = commentList
            .map(comment -> CommentGetResponse.from(CommentDto.from(comment), MemberInfoDto.from(UserDto.from(comment.getUser()))));

        return PageResponse.from(responsePage);
    }

    // 댓글 수정
    @Transactional
    public CommentUpdateResponse updateComment(long taskId, long commentId, String username, CommentUpdateRequest request) {

        Task task = taskRepository.findTaskById(taskId);
        Comment comment = commentRepository.findCommentById(commentId, COMMENT_NOT_FOUND_COMMENT);

        checkTaskCommentRelationship(task, comment);
        checkCommentUserRelationship(username, comment);

        comment.update(request);
        commentRepository.saveAndFlush(comment);

        return CommentUpdateResponse.from(CommentDto.from(comment));
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(long taskId, long commentId, String username) {

        Task task = taskRepository.findTaskById(taskId);
        Comment comment = commentRepository.findCommentById(commentId, COMMENT_NOT_FOUND_COMMENT);

        checkTaskCommentRelationship(task, comment);
        checkCommentUserRelationship(username, comment);

        if (comment.isDeleted()) { throw new CustomException(COMMENT_NO_PERMISSION_DELETE); }

        if (commentRepository.existsByParentCommentId(comment.getId())) {
            List<Comment> childCommentList = commentRepository.findAllByParentCommentId(comment.getId());
            childCommentList.forEach(BaseEntity::updateIsDeleted);
        }

        comment.updateIsDeleted();
    }

    // Comment가 Task의 댓글인지 확인
    private void checkTaskCommentRelationship(Task task, Comment parentComment) {
        if (!Objects.equals(task.getId(), parentComment.getTask().getId())) {
            throw new CustomException(COMMENT_NOT_FOUND_TASK_OR_COMMENT);
        }
    }

    // User가 Comment의 작성자인지 확인
    private void checkCommentUserRelationship(String username, Comment comment) {
        if (!Objects.equals(comment.getUser().getUsername(), username)) {
            throw new CustomException(COMMENT_NO_PERMISSION_UPDATE);
        }
    }
}
