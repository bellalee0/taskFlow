package com.example.taskflow.domain.comment.repository;

import static com.example.taskflow.common.exception.ErrorMessage.*;

import com.example.taskflow.common.entity.Comment;
import com.example.taskflow.common.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findCommentById(long id) {
        return findById(id).orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
    }

    Page<Comment> findByTaskId(long taskId, Pageable pageable);
}
