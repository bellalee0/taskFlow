package com.example.taskflow.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    // 400
    TASK_WRONG_ENUM(HttpStatus.BAD_REQUEST, "유효하지 않은 상태 값입니다."),

    // 401
    AUTH_WRONG_EMAIL_AND_PASSWORD(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."),
    AUTH_WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."),
    TOKEN_REQUIRED_FIELD(HttpStatus.UNAUTHORIZED, "토큰이 필요합니다."),
    TOKEN_INVALID_FIELD(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    // 403
    COMMENT_NO_PERMISSION_UPDATE(HttpStatus.FORBIDDEN, "댓글을 수정할 권한이 없습니다."),
    COMMENT_NO_PERMISSION_DELETE(HttpStatus.FORBIDDEN, "댓글을 삭제할 권한이 없습니다."),

    // 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "팀을 찾을 수 없습니다."),
    TEAMUSER_NOT_FOUND(HttpStatus.NOT_FOUND, "팀 멤버를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND_TASK_OR_COMMENT(HttpStatus.NOT_FOUND, "작업을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    ASSIGNEE_NOT_FOUND(HttpStatus.NOT_FOUND, "담당자를 찾을 수 없습니다."),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "작업을 찾을 수 없습니다."),

    // 409
    USER_USED_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 사용자명입니다."),
    USER_USED_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    TEAM_ALREADY_PRESENT(HttpStatus.CONFLICT, "이미 존재하는 팀 이름입니다."),
    TEAM_HAS_USER_WHEN_DELETE(HttpStatus.CONFLICT, "팀에 멤버가 존재하여 삭제할 수 없습니다."),
    TEAMUSER_ALREADY_PRESENCE(HttpStatus.CONFLICT, "이미 팀에 속한 멤버입니다.");


    private final HttpStatus status;
    private final String message;
}
