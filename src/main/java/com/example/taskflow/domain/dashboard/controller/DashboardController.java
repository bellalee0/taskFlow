package com.example.taskflow.domain.dashboard.controller;

import com.example.taskflow.common.model.enums.SuccessMessage;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.dashboard.model.response.DashboardGetStatsResponse;
import com.example.taskflow.domain.dashboard.model.response.DashboardGetUserTaskSummaryResponse;
import com.example.taskflow.domain.dashboard.model.response.DashboardGetWeeklyTrendResponse;
import com.example.taskflow.domain.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    //대시보드 통계 기능
    @GetMapping("/stats")
    public ResponseEntity<GlobalResponse<DashboardGetStatsResponse>> getDashboardStatsApi(
            @AuthenticationPrincipal User user
    ) {
        DashboardGetStatsResponse result = dashboardService.getDashboardStats(user.getUsername());

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.DASHBOARD_GET_STATS_SUCCESS, result));
    }

    //내 작업 요약
    @GetMapping("/tasks")
    public ResponseEntity<GlobalResponse<DashboardGetUserTaskSummaryResponse>> getUserTaskSummaryApi(
            @AuthenticationPrincipal User user
    ) {
        DashboardGetUserTaskSummaryResponse result = dashboardService.getUserTaskSummary(user.getUsername());
        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.DASHBOARD_GET_TASKS_SUCCESS, result));
    }

    //주간 작업 추세
    @GetMapping("/weekly-trend")
    public ResponseEntity<GlobalResponse<List<DashboardGetWeeklyTrendResponse>>> getWeeklyTrendApi(
    ) {
        List<DashboardGetWeeklyTrendResponse> result = dashboardService.getWeeklyTrend();
        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.DASHBOARD_GET_WEEKLY_TREND_SUCCESS, result));
    }

}