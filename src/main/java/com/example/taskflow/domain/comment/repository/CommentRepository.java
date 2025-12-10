package com.example.taskflow.domain.comment.repository;

import com.example.taskflow.common.entity.Comment;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.exception.ErrorMessage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByTaskId(long taskId, Pageable pageable);

    boolean existsByParentCommentId(Long id);

    List<Comment> findAllByParentCommentId(Long id);

    default Comment findCommentById(long id, ErrorMessage error) {
        return findById(id).orElseThrow(() -> new CustomException(error));
    }
    List<Comment> findByTaskId(Long id);
}
