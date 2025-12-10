package com.example.taskflow.domain.comment.model.dto;

import com.example.taskflow.common.entity.Comment;
import java.time.LocalDateTime;
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
    private Comment parentComment;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static CommentDto from(Comment comment) {
        return new CommentDto(
            comment.getId(),
            comment.getTask().getId(),
            comment.getUser().getId(),
            comment.getContent(),
            comment.getParentComment(),
            comment.isDeleted(),
            comment.getCreatedAt(),
            comment.getModifiedAt()
        );
    }
}
