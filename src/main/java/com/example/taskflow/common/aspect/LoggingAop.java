package com.example.taskflow.common.aspect;

import com.example.taskflow.common.annotation.Loggable;
import com.example.taskflow.common.entity.Log;
import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.model.enums.LogType;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.activities.repository.LogRepository;
import com.example.taskflow.domain.comment.model.response.CommentCreateResponse;
import com.example.taskflow.domain.comment.model.response.CommentUpdateResponse;
import com.example.taskflow.domain.task.model.response.TaskCreateResponse;
import com.example.taskflow.domain.task.model.response.TaskUpdateResponse;
import com.example.taskflow.domain.task.model.response.TaskUpdateStatusResponse;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "LoggingAop")
@RequiredArgsConstructor
public class LoggingAop {

    private final LogRepository logRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    // Task, Comment 관련 작업 로깅
    @Around("@annotation(loggable)")
    public Object logExecution(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {

        long start = System.currentTimeMillis();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            logInfo(joinPoint, start, username, result, true);
            throw throwable;
        }

        logInfo(joinPoint, start, username, result, false);

        return result;
    }

    // 콘솔에 활동 로그 남기기
    private void logInfo(ProceedingJoinPoint joinPoint, long start, String username, Object result, boolean exception) {

        long end = System.currentTimeMillis();

        log.info(" 활동 로그 : 실행 시간={}ms | 호출자={} | 메서드명={}.{} | 입력 파라미터={} | 실행 결과={} | 예외 발생 여부={}",
            (end - start),
            username,
            joinPoint.getSignature().getDeclaringType().getSimpleName(),
            joinPoint.getSignature().getName(),
            joinPoint.getArgs(),
            result,
            exception
        );
    }
}
