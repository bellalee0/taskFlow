package com.example.taskflow.common.aspect;

import com.example.taskflow.common.annotation.Loggable;
import com.example.taskflow.common.entity.Log;
import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.model.enums.LogType;
import com.example.taskflow.domain.activities.repository.LogRepository;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "LoggingAop")
@RequiredArgsConstructor
public class LoggingAop {

    private final LogRepository logRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Around("@annotation(loggable)")
    public Object logExecution(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {

        boolean exception = false;
        long start = System.currentTimeMillis();
        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            exception = true;
        }

        long end = System.currentTimeMillis();

        log.info(" 활동 로그 | 실행 시간={}ms | 호출자= | 메서드명 = . | 입력 파라미터={} | 실행 결과={} | 예외 발생={}",
            (end - start),
            joinPoint.getArgs(),
            result,
            exception
        );

        LogType type = loggable.logType();

        long userId = 1L;
        User user = userRepository.findUserById(userId);

        long taskId = 1L;
        Task task = taskRepository.findTaskById(taskId);

        String description = "설명";

        Log log = new Log(type, user, task, description);
        logRepository.save(log);

        return result;
    }
}
