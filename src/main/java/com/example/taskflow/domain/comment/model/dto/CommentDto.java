package com.example.taskflow.domain.comment.model.dto;

import com.example.taskflow.common.entity.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {

    private final Long id;
    private final Long taskId;
    private final Long userId;
    private final String content;
    private final Comment parentComment;
    private final boolean isDeleted;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

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
