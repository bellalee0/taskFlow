package com.example.taskflow.domain.dashboard.controller;

import com.example.taskflow.common.model.enums.SuccessMessage;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.dashboard.model.response.DashboardStatsResponse;
import com.example.taskflow.domain.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    //대시보드 통계 기능
    @GetMapping("/stats/{userId}")/*병합시 pathvariable 삭제*/
    public ResponseEntity<GlobalResponse<DashboardStatsResponse>> getDashboardStatsApi (
            /*@AuthenticationPrincipal AuthUser authUser*/@PathVariable Long userId
    ) {
        DashboardStatsResponse result = dashboardService.getDashboardStats(/*authUser.getId()*/userId);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.DASHBOARD_GET_STATS_SUCCESS, result));
    }

}