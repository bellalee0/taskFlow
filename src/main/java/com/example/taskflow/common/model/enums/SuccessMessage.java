package com.example.taskflow.common.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    // 200 OK
    LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
    CHECK_PASSWORD_SUCCESS(HttpStatus.OK, "비밀번호가 확인되었습니다."),

    GET_USER_SUCCESS(HttpStatus.OK, "사용자 정보 조회 성공"),
    GET_USER_LIST_SUCCESS(HttpStatus.OK, "사용자 목록 조회 성공"),
    UPDATE_USER_SUCCESS(HttpStatus.OK, "사용자 정보가 수정되었습니다."),
    DELETE_USER_SUCCESS(HttpStatus.OK, "회원 탈퇴가 완료되었습니다."),
    GET_AVAILABLE_USER_LIST_SUCCESS(HttpStatus.OK, "추가 가능한 사용자 목록 조회 성공"),

    GET_TASK_LIST_SUCCESS(HttpStatus.OK, "작업 목록 조회 성공"),
    GET_TASK_SUCCESS(HttpStatus.OK, "작업 조회 성공"),
    UPDATE_TASK_SUCCESS(HttpStatus.OK, "작업이 수정되었습니다."),
    DELETE_TASK_SUCCESS(HttpStatus.OK, "작업이 삭제되었습니다."),
    DELETE_TASK_STATUS_SUCCESS(HttpStatus.OK, "작업 상태가 변경되었습니다."),

    GET_TEAM_LIST_SUCCESS(HttpStatus.OK, "팀 목록 조회 성공"),
    GET_TEAM_SUCCESS(HttpStatus.OK, "팀 조회 성공"),
    GET_TEAM_MEMBER_LIST_SUCCESS(HttpStatus.OK, "팀 멤버 조회 성공"),
    UPDATE_TEAM_SUCCESS(HttpStatus.OK, "팀 정보가 수정되었습니다."),
    DELETE_TEAM_SUCCESS(HttpStatus.OK, "팀이 삭제되었습니다."),
    ADD_TEAM_MEMBER_SUCCESS(HttpStatus.OK, "팀 멤버가 추가되었습니다."),
    DELETE_TEAM_MEMBER_SUCCESS(HttpStatus.OK, "팀 멤버가 제거되었습니다."),

    GET_COMMENT_LIST_SUCCESS(HttpStatus.OK, "댓글 목록 조회 성공"),
    UPDATE_COMMENT_SUCCESS(HttpStatus.OK, "댓글이 수정되었습니다."),
    DELETE_COMMENT_SUCCESS(HttpStatus.OK, "댓글이 삭제되었습니다."),

    GET_DASHBOARD_STATS_SUCCESS(HttpStatus.OK, "대시보드 통계 조회 성공"),
    GET_DASHBOARD_TASKS_SUCCESS(HttpStatus.OK, "내 작업 요약 조회 성공"),
    GET_DASHBOARD_WEEKLY_TREND_SUCCESS(HttpStatus.OK, "주간 작업 추세 조회 성공"),

    GET_ALL_ACTIVITIES_SUCCESS(HttpStatus.OK, "활동 로그 조회 성공"),
    GET_MY_ACTIVITIES_SUCCESS(HttpStatus.OK, "내 활동 로그 조회 성공"),
    GET_SEARCH_SUCCESS(HttpStatus.OK, "검색 성공"),


    // 201 CREATED
    CREATE_USER_SUCCESS(HttpStatus.CREATED, "회원가입이 완료되었습니다."),
    CREATE_TASK_SUCCESS(HttpStatus.CREATED, "작업이 생성되었습니다."),
    CREATE_TEAM_SUCCESS(HttpStatus.CREATED, "팀이 생성되었습니다."),
    CREATE_COMMENT_SUCCESS(HttpStatus.CREATED, "댓글이 작성되었습니다.");


    private final HttpStatus status;
    private final String message;
}



