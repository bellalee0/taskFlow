package com.example.taskflow.domain.comment.repository;

import com.example.taskflow.common.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTaskId(Long id);
}
