package com.example.taskflow.domain.comment.repository;

import static com.example.taskflow.common.exception.ErrorMessage.COMMENT_NOT_FOUND_COMMENT;
import static com.example.taskflow.common.exception.ErrorMessage.COMMENT_NOT_FOUND_TASK_OR_COMMENT;

import com.example.taskflow.common.entity.Comment;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.exception.ErrorMessage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    boolean existsByParentCommentId(Long id);

    @Query("""
          SELECT c FROM Comment c
          WHERE c.parentComment is null and c.task.id = :taskId
          """)
    Page<Comment> findByTaskId(@Param("taskId") long taskId, Pageable pageable);

    List<Comment> findAllByParentCommentId(Long id);

    List<Comment> findByTaskId(Long id);

    List<Comment> findAllByUser(User user);

    default Comment findCommentById(long id) {
        return findById(id).orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND_COMMENT));
    }

    default Comment findParentCommentById(long id) {
        return findById(id).orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND_TASK_OR_COMMENT));
    }
}
