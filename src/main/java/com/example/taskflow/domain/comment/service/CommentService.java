package com.example.taskflow.domain.comment.service;

import static com.example.taskflow.common.exception.ErrorMessage.*;

import com.example.taskflow.common.entity.Comment;
import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.domain.comment.model.dto.CommentDto;
import com.example.taskflow.domain.comment.model.request.CommentCreateRequest;
import com.example.taskflow.domain.comment.model.response.CommentCreateResponse;
import com.example.taskflow.domain.comment.repository.CommentRepository;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.model.dto.UserDto;
import com.example.taskflow.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
            if (parentComment.getDepth() != 0) { throw new CustomException(COMMENT_NOT_FOUND); }
        }

        Comment comment = new Comment(request.getContent(), user, task, parentComment);

        commentRepository.save(comment);

        // TODO: UserDto 요구사항에 맞게 변경 필요(id, username, name)
        return CommentCreateResponse.from(CommentDto.from(comment), UserDto.from(user));
    }
}
