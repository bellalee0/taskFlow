package com.example.taskflow.domain.comment.model.request;

import com.example.taskflow.common.exception.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequest {
    @NotBlank(message = ValidationMessage.COMMENT_CONTENT_NOT_BLANK)
    private String content;
}
