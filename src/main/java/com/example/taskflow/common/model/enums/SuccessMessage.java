package com.example.taskflow.common.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    // 200 OK
    AUTH_LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
    AUTH_CHECK_PASSWORD_SUCCESS(HttpStatus.OK, "비밀번호가 확인되었습니다."),

    USER_GET_SUCCESS(HttpStatus.OK, "사용자 정보 조회 성공"),
    USER_GET_LIST_SUCCESS(HttpStatus.OK, "사용자 목록 조회 성공"),
    USER_UPDATE_SUCCESS(HttpStatus.OK, "사용자 정보가 수정되었습니다."),
    USER_DELETE_SUCCESS(HttpStatus.OK, "회원 탈퇴가 완료되었습니다."),
    USER_GET_AVAILABLE_LIST_SUCCESS(HttpStatus.OK, "추가 가능한 사용자 목록 조회 성공"),

    TASK_GET_LIST_SUCCESS(HttpStatus.OK, "작업 목록 조회 성공"),
    TASK_GET_ONE_SUCCESS(HttpStatus.OK, "작업 조회 성공"),
    TASK_UPDATE_SUCCESS(HttpStatus.OK, "작업이 수정되었습니다."),
    TASK_DELETE_SUCCESS(HttpStatus.OK, "작업이 삭제되었습니다."),
    TASK_DELETE_STATUS_SUCCESS(HttpStatus.OK, "작업 상태가 변경되었습니다."),

    TEAM_GET_LIST_SUCCESS(HttpStatus.OK, "팀 목록 조회 성공"),
    TEAM_GET_ONE_SUCCESS(HttpStatus.OK, "팀 조회 성공"),
    TEAM_GET_MEMBER_LIST_SUCCESS(HttpStatus.OK, "팀 멤버 조회 성공"),
    TEAM_UPDATE_SUCCESS(HttpStatus.OK, "팀 정보가 수정되었습니다."),
    TEAM_DELETE_SUCCESS(HttpStatus.OK, "팀이 삭제되었습니다."),
    TEAM_ADD_MEMBER_SUCCESS(HttpStatus.OK, "팀 멤버가 추가되었습니다."),
    TEAM_DELETE_MEMBER_SUCCESS(HttpStatus.OK, "팀 멤버가 제거되었습니다."),

    COMMENT_GET_LIST_SUCCESS(HttpStatus.OK, "댓글 목록을 조회했습니다."),
    COMMENT_UPDATE_SUCCESS(HttpStatus.OK, "댓글이 수정되었습니다."),
    COMMENT_DELETE_SUCCESS(HttpStatus.OK, "댓글이 삭제되었습니다."),

    DASHBOARD_GET_STATS_SUCCESS(HttpStatus.OK, "대시보드 통계 조회 성공"),
    DASHBOARD_GET_TASKS_SUCCESS(HttpStatus.OK, "내 작업 요약 조회 성공"),
    DASHBOARD_GET_WEEKLY_TREND_SUCCESS(HttpStatus.OK, "주간 작업 추세 조회 성공"),

    ACTIVITIES_GET_ALL_SUCCESS(HttpStatus.OK, "활동 로그 조회 성공"),
    ACTIVITIES_GET_MINE_SUCCESS(HttpStatus.OK, "내 활동 로그 조회 성공"),

    SEARCH_GET_SUCCESS(HttpStatus.OK, "검색 성공"),


    // 201 CREATED
    USER_CREATE_SUCCESS(HttpStatus.CREATED, "회원가입이 완료되었습니다."),
    TASK_CREATE_SUCCESS(HttpStatus.CREATED, "작업이 생성되었습니다."),
    TEAM_CREATE_SUCCESS(HttpStatus.CREATED, "팀이 생성되었습니다."),
    COMMENT_CREATE_SUCCESS(HttpStatus.CREATED, "댓글이 작성되었습니다.");


    private final HttpStatus status;
    private final String message;
}



