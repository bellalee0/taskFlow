package com.example.taskflow.domain.comment.model.response;

import com.example.taskflow.domain.comment.model.dto.CommentDto;
import com.example.taskflow.domain.team.model.response.MemberIdUsernameNameEmailRoleResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentGetResponse {

    private Long id;
    private String content;
    private Long taskId;
    private Long userId;
    private MemberIdUsernameNameEmailRoleResponse user;
    private Long parentId;
    private LocalDateTime createdAT;
    private LocalDateTime updatedAT;

    public static CommentGetResponse from(CommentDto commentDto, MemberIdUsernameNameEmailRoleResponse userDto) {
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
