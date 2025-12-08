package com.example.taskflow.domain.comment.model.dto;

import com.example.taskflow.common.entity.Comment;
import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private Long taskId;
    private Long userId;
    private String content;
    private Long parentCommentId;
    private int depth;

    public static CommentDto from(Comment comment) {
        return new CommentDto(
            comment.getId(),
            comment.getTask().getId(),
            comment.getUser().getId(),
            comment.getContent(),
            comment.getParentComment() == null ? 0L : comment.getParentComment().getId(),
            comment.getDepth()
        );
    }
}
