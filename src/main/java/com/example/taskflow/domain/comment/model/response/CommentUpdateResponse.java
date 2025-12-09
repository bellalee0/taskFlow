package com.example.taskflow.domain.comment.model.response;

import com.example.taskflow.domain.comment.model.dto.CommentDto;
import com.example.taskflow.domain.user.model.dto.UserDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateResponse {

    private Long id;
    private Long taskId;
    private Long userId;
    private String content;
    private Long parentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


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
