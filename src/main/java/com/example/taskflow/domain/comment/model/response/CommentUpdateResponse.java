package com.example.taskflow.domain.comment.model.response;

import com.example.taskflow.domain.comment.model.dto.CommentDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUpdateResponse {

    private final Long id;
    private final Long taskId;
    private final Long userId;
    private final String content;
    private final Long parentId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CommentUpdateResponse from(CommentDto commentDto) {
        return new CommentUpdateResponse(
            commentDto.getId(),
            commentDto.getTaskId(),
            commentDto.getUserId(),
            commentDto.getContent(),
            commentDto.getParentComment() == null ? null : commentDto.getParentComment().getId(),
            commentDto.getCreatedAt(),
            commentDto.getModifiedAt()
        );
    }
}
