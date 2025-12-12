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
public class CommentGetResponse {

    private final Long id;
    private final String content;
    private final Long taskId;
    private final Long userId;
    private final UserInfoDto user;
    private final Long parentId;
    private final LocalDateTime createdAT;
    private final LocalDateTime updatedAT;

    public static CommentGetResponse from(CommentDto commentDto, UserInfoDto userDto) {
        return new CommentGetResponse(
            commentDto.getId(),
            commentDto.getContent(),
            commentDto.getTaskId(),
            commentDto.getUserId(),
            userDto,
            commentDto.getParentComment() == null ? null : commentDto.getParentComment().getId(),
            commentDto.getCreatedAt(),
            commentDto.getModifiedAt()
        );
    }
}
