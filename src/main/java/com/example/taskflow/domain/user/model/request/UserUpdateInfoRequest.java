package com.example.taskflow.domain.user.model.request;

import com.example.taskflow.common.model.enums.ValidationMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserUpdateInfoRequest {

    @NotBlank(message = ValidationMessage.NOT_BLANK_DEFAULT)
    @Email(message = ValidationMessage.EMAIL_FORMAT)
    private String email;

    @NotBlank(message = ValidationMessage.NOT_BLANK_DEFAULT)
    @Pattern(regexp = ValidationMessage.PASSWORD_REGEXP, message = ValidationMessage.PASSWORD_PATTERN_MESSAGE)
    private String password;

    @NotBlank(message = ValidationMessage.NOT_BLANK_DEFAULT)
    private String name;
}
