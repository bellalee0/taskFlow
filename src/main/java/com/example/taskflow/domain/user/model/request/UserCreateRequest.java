package com.example.taskflow.domain.user.model.request;

import com.example.taskflow.common.model.enums.ValidationMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserCreateRequest {

    @NotBlank(message = ValidationMessage.USERNAME_NOT_BLANK)
    @Size(max = 10, message = ValidationMessage.USERNAME_SIZE)
    private String username;

    @Email(message = ValidationMessage.EMAIL_FORMAT)
    private String email;

    @NotBlank(message = ValidationMessage.PASSWORD_NOT_BLANK)
    @Pattern(regexp = ValidationMessage.PASSWORD_REGEXP, message = ValidationMessage.PASSWORD_PATTERN_MESSAGE)
    private String password;

    @NotBlank(message = ValidationMessage.NAME_NOT_BLANK)
    private String name;
}
