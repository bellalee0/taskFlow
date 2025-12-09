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
public class CommentGetResponse {

    private Long id;
    private String content;
    private Long taskId;
    private Long userId;
    private UserDto user;
    private Long parentId;
    private LocalDateTime createdAT;
    private LocalDateTime updatedAT;

    public static CommentGetResponse from(CommentDto commentDto, UserDto userDto) {
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
