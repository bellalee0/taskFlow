package com.example.taskflow.domain.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequest {



    @NotBlank
    @Size(max = 10, message = "닉네임은 10글자를 넘길 수 없습니다.")
    private String userName;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%])(?=\\S+$).{4,15}$", message = "비밀번호는 영어와 숫자, 특수문자를 최소 1개 이상 포함해서 4~15자리 이내로 입력해주세요.")
    private String password;

    @NotBlank
    private String name;


}
