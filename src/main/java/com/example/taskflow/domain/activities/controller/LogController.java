package com.example.taskflow.domain.activities.controller;

import com.example.taskflow.common.model.enums.LogType;
import com.example.taskflow.common.model.enums.SuccessMessage;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.domain.activities.model.response.LogGetAllResponse;
import com.example.taskflow.domain.activities.model.response.LogGetMineResponse;
import com.example.taskflow.domain.activities.service.LogService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    // 전체 활동 로그 조회
    @GetMapping
    public ResponseEntity<GlobalResponse<PageResponse<LogGetAllResponse>>> getAllLogsApi(
        @PageableDefault(page = 0, size = 10) Pageable pageable,
        // TODO: LogType 전달 방식 확인 필요
        @RequestParam(required = false) LogType type,
        @RequestParam(required = false) Long taskId,
        // TODO: 프론트에서 시간 설정해서 주는지 확인 필요(날짜만 전달한다면 시간 지정 필요)
        @RequestParam(required = false) LocalDateTime startDate,
        @RequestParam(required = false) LocalDateTime endDate
    ) {
        PageResponse<LogGetAllResponse> result = logService.getAllLogs(pageable, type, taskId, startDate, endDate);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.ACTIVITIES_GET_ALL_SUCCESS, result));
    }

    // 내 활동 로그 조회
//    @GetMapping("/me")
    @GetMapping("/me/{userId}")
    public ResponseEntity<GlobalResponse<List<LogGetMineResponse>>> getMyLogsApi(
        // TODO: 로그인 유저 정보로 변경
        @PathVariable long userId
    ) {
        List<LogGetMineResponse> result = logService.getMyLogs(userId);

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.ACTIVITIES_GET_MINE_SUCCESS, result));
    }
}
