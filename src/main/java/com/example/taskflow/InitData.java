package com.example.taskflow;

import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitData {

    //private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @PostConstruct
    @Transactional
    public void init() {
        // --- Users ---
        User user1 = new User("alpha", "alpha@test.com", "1234", "알파");
        User user2 = new User("beta", "beta@test.com", "1234", "베타");

        userRepository.save(user1);
        userRepository.save(user2);

        // --- Tasks ---
        Task task1 = new Task(
            "서버 점검",
            "일일 서버 상태 확인",
            TaskPriority.HIGH,
            user1,
            LocalDateTime.now().plusDays(1)
        );
        Task task2 = new Task(
            "사내 문서 정리",
            "온보딩 문서 업데이트",
            TaskPriority.MEDIUM,
            user2,
            LocalDateTime.now().plusDays(2)
        );
        Task task3 = new Task(
            "로그 모니터링 개선",
            "알람 규칙 수정",
            TaskPriority.LOW,
            user1,
            LocalDateTime.now().plusDays(3)
        );
        Task task4 = new Task(
                "코드 리펙토링",
                "매서드 최적화",
                TaskPriority.LOW,
                user1,
                LocalDateTime.now().minusDays(3)
        );
        task4.updateStatus(TaskStatus.DONE);

        Task task5 = new Task(
                "대시보드 기능 풀리퀘스트",
                "대시보드 기능 구현 완료",
                TaskPriority.MEDIUM,
                user1,
                LocalDateTime.now().minusDays(2)
        );
        Task task6 = new Task(
                "대시보드 기능 테스트",
                "대시보드 기능 테스트",
                TaskPriority.MEDIUM,
                user1,
                LocalDateTime.now().minusDays(2)
        );

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        taskRepository.save(task4);
        taskRepository.save(task5);
        taskRepository.save(task6);
    }
}
