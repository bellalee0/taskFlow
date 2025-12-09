package com.example.taskflow.domain.comment.model.response;

import com.example.taskflow.domain.comment.model.dto.CommentDto;
import com.example.taskflow.domain.user.model.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentCreateResponse {

    private Long id;
    private Long taskId;
    private Long userId;
    private UserDto user; // TODO: User 응답 객체로 변경
    private String content;
    private Long parentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentCreateResponse from(CommentDto commentDto, UserDto userDto) {
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
