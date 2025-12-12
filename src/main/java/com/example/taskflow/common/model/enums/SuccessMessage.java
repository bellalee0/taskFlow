package com.example.taskflow.common.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    // 200 OK
    AUTH_LOGIN_SUCCESS("로그인 성공"),
    AUTH_CHECK_PASSWORD_SUCCESS("비밀번호가 확인되었습니다."),

    USER_GET_SUCCESS("사용자 정보 조회 성공"),
    USER_GET_LIST_SUCCESS("사용자 목록 조회 성공"),
    USER_UPDATE_SUCCESS("사용자 정보가 수정되었습니다."),
    USER_DELETE_SUCCESS("회원 탈퇴가 완료되었습니다."),
    USER_GET_AVAILABLE_LIST_SUCCESS("추가 가능한 사용자 목록 조회 성공"),

    TASK_GET_LIST_SUCCESS("작업 목록 조회 성공"),
    TASK_GET_ONE_SUCCESS("작업 조회 성공"),
    TASK_UPDATE_SUCCESS("작업이 수정되었습니다."),
    TASK_DELETE_SUCCESS("작업이 삭제되었습니다."),
    TASK_UPDATE_STATUS_SUCCESS("작업 상태가 변경되었습니다."),

    TEAM_GET_LIST_SUCCESS("팀 목록 조회 성공"),
    TEAM_GET_ONE_SUCCESS("팀 조회 성공"),
    TEAM_GET_MEMBER_LIST_SUCCESS("팀 멤버 조회 성공"),
    TEAM_UPDATE_SUCCESS("팀 정보가 수정되었습니다."),
    TEAM_DELETE_SUCCESS("팀이 삭제되었습니다."),
    TEAM_ADD_MEMBER_SUCCESS("팀 멤버가 추가되었습니다."),
    TEAM_DELETE_MEMBER_SUCCESS("팀 멤버가 제거되었습니다."),

    COMMENT_GET_LIST_SUCCESS("댓글 목록을 조회했습니다."),
    COMMENT_UPDATE_SUCCESS("댓글이 수정되었습니다."),
    COMMENT_DELETE_SUCCESS("댓글이 삭제되었습니다."),

    DASHBOARD_GET_STATS_SUCCESS("대시보드 통계 조회 성공"),
    DASHBOARD_GET_TASKS_SUCCESS("내 작업 요약 조회 성공"),
    DASHBOARD_GET_WEEKLY_TREND_SUCCESS("주간 작업 추세 조회 성공"),

    ACTIVITIES_GET_ALL_SUCCESS("활동 로그 조회 성공"),
    ACTIVITIES_GET_MINE_SUCCESS("내 활동 로그 조회 성공"),

    SEARCH_GET_SUCCESS("검색 성공"),


    // 201 CREATED
    USER_CREATE_SUCCESS("회원가입이 완료되었습니다."),
    TASK_CREATE_SUCCESS("작업이 생성되었습니다."),
    TEAM_CREATE_SUCCESS("팀이 생성되었습니다."),
    COMMENT_CREATE_SUCCESS("댓글이 작성되었습니다.");


    private final String message;
}



