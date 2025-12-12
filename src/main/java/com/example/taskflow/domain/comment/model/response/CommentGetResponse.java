package com.example.taskflow.domain.comment.model.response;

import com.example.taskflow.domain.comment.model.dto.CommentDto;
import com.example.taskflow.domain.team.model.dto.MemberInfoDto;
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
    private final MemberInfoDto user;
    private final Long parentId;
    private final LocalDateTime createdAT;
    private final LocalDateTime updatedAT;

    public static CommentGetResponse from(CommentDto commentDto, MemberInfoDto userDto) {
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
