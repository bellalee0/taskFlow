package com.example.taskflow.domain.activities.service;

import com.example.taskflow.common.entity.Log;
import com.example.taskflow.common.model.enums.LogType;
import com.example.taskflow.common.model.response.PageResponse;
import com.example.taskflow.domain.activities.model.dto.LogDto;
import com.example.taskflow.domain.activities.model.response.LogGetAllResponse;
import com.example.taskflow.domain.activities.repository.LogRepository;
import com.example.taskflow.domain.user.model.dto.UserDto;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    // 전체 활동 로그 조회
    @Transactional(readOnly = true)
    public PageResponse<LogGetAllResponse> getAllLogs(Pageable pageable, LogType type, Long taskId, LocalDateTime startDate, LocalDateTime endDate) {

        Page<Log> logPage = logRepository.findByFilters(pageable, type, taskId, startDate, endDate);

        Page<LogGetAllResponse> responsePage = logPage
            .map(log -> LogGetAllResponse.from(LogDto.from(log), UserDto.from(log.getUser())));

        return PageResponse.from(responsePage);
    }
}
