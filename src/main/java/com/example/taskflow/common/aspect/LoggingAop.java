package com.example.taskflow.common.aspect;

import com.example.taskflow.common.annotation.Loggable;
import com.example.taskflow.common.entity.Log;
import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.model.enums.LogType;
import com.example.taskflow.domain.activities.repository.LogRepository;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Aspect
@Component
@Slf4j(topic = "LoggingAop")
@RequiredArgsConstructor
public class LoggingAop {

    private final LogRepository logRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @After("@annotation(loggable)")
    public void logBefore(Loggable loggable) {

        LogType type = loggable.logType();

        // TODO: 로그인한 사용자 정보로 변경
        long userId = 1L;
        User user = userRepository.findUserById(userId);

        // TODO: 작업한 task ID 가져오기
        long taskId = 1L;
        Task task = taskRepository.findTaskById(taskId);

        String description = "설명";

        Log log = new Log(type, user, task, description);
        logRepository.save(log);


//        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
//        RequestContextHolder.getRequestAttributes())).getRequest();
//        ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request;
//
//        String userId = request.getAttribute("userId").toString();
//        String requestUri = request.getRequestURI();
//        LocalDateTime requestTime = LocalDateTime.now();
//
//        log.info("관리자 페이지 접근: userId={}, URI={}, requestTime={}, requestBody={}", userId, requestUri, requestTime, requestBody);
    }
}
