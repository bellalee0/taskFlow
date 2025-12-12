package com.example.taskflow.domain.comment.model.response;

import com.example.taskflow.domain.comment.model.dto.CommentDto;
import com.example.taskflow.domain.comment.model.dto.UserInfoDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentCreateResponse {

    private final Long id;
    private final Long taskId;
    private final Long userId;
    private final UserInfoDto user;
    private final String content;
    private final Long parentId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CommentCreateResponse from(CommentDto commentDto, UserInfoDto userDto) {
        return new CommentCreateResponse(
            commentDto.getId(),
            commentDto.getTaskId(),
            commentDto.getUserId(),
            userDto,
            commentDto.getContent(),
            commentDto.getParentComment() == null ? null : commentDto.getParentComment().getId(),
            commentDto.getCreatedAt(),
            commentDto.getModifiedAt()
        );
    }
}
