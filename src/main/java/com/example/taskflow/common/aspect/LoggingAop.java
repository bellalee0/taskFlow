package com.example.taskflow.common.aspect;

import com.example.taskflow.common.annotation.Loggable;
import com.example.taskflow.common.entity.Log;
import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.model.enums.LogType;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.activities.repository.LogRepository;
import com.example.taskflow.domain.comment.model.response.*;
import com.example.taskflow.domain.task.model.response.*;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        
        saveLog(loggable, username, (ResponseEntity) result);

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

    // DB 형식에 맞게 로그 저장
    private void saveLog(Loggable loggable, String username, ResponseEntity result) {
        LogType logType = loggable.logType();

        User user = userRepository.findUserByUsername(username);

        FindTaskDescriptionByLogType findByLogType = getResult(result, logType);

        Log log = new Log(logType, user, findByLogType.task, findByLogType.description);

        logRepository.save(log);
    }

    // 로그타입에 따라 taskId, Description 조회 및 수정
    private FindTaskDescriptionByLogType getResult(ResponseEntity result, LogType logType) {

        GlobalResponse globalResponse = (GlobalResponse) result.getBody();

        Long taskId = null;
        String description = logType.getDescription();

        switch (logType) {

            case TASK_CREATED:
                TaskCreateResponse taskCreateResponse = (TaskCreateResponse) globalResponse.getData();
                taskId = taskCreateResponse.getId();
                description = description.replace("{title}", taskCreateResponse.getTitle());
                break;

            case TASK_UPDATED:
                TaskUpdateResponse taskUpdateResponse = (TaskUpdateResponse) globalResponse.getData();
                taskId = taskUpdateResponse.getId();
                description = description.replace("{title}", taskUpdateResponse.getTitle());
                break;

            case TASK_DELETED:
                HttpServletRequest httpServletRequest = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
                String[] requestURI = httpServletRequest.getRequestURI().split("/");
                taskId = Long.parseLong(requestURI[requestURI.length - 1]);
                Task deletedTask = taskRepository.findTaskById(taskId);
                description = description.replace("{title}", deletedTask.getTitle());
                break;

            case TASK_STATUS_CHANGED:
                TaskUpdateStatusResponse taskUpdateStatusResponse = (TaskUpdateStatusResponse) globalResponse.getData();
                taskId = taskUpdateStatusResponse.getId();
                // TODO: oldStatus 제대로 반영 안됨
                Task oldTask = taskRepository.findTaskById(taskId);
                description = description.replace("{oldStatus}", oldTask.getStatus().toString());
                description = description.replace("{newStatus}", taskUpdateStatusResponse.getStatus().toString());
                break;

            case COMMENT_CREATED:
                CommentCreateResponse commentCreateResponse = (CommentCreateResponse) globalResponse.getData();
                taskId = commentCreateResponse.getTaskId();
                Task task = taskRepository.findTaskById(taskId);
                description = description.replace("{title}", task.getTitle());
                break;

            case COMMENT_UPDATED:
                CommentUpdateResponse commentUpdateResponse = (CommentUpdateResponse) globalResponse.getData();
                taskId = commentUpdateResponse.getTaskId();
                break;

            case COMMENT_DELETED:
                HttpServletRequest commentHttpServletRequest = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
                String[] commentRequestURI = commentHttpServletRequest.getRequestURI().split("/");
                taskId = Long.parseLong(commentRequestURI[3]);
                break;
        }

        Task task = taskRepository.findTaskById(taskId);

        return new FindTaskDescriptionByLogType(task, description);
    }

    // 내부에서 사용하는 임시 클래스
    private record FindTaskDescriptionByLogType(Task task, String description) {

    }
}
