package com.example.taskflow.domain.user.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserUpdateInfoRequest {

    @NotBlank(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%])(?=\\S+$).{8,}$", message = "비밀번호는 영어와 숫자, 특수문자를 최소 1개 이상 포함해서 8자리 이상 입력해주세요.")
    private String password;

    @NotBlank
    private String name;
}
