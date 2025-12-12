package com.example.taskflow;


import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.Team;
import com.example.taskflow.common.entity.TeamUser;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import com.example.taskflow.common.model.enums.UserRole;
import com.example.taskflow.common.utils.PasswordEncoder;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.team.repository.TeamRepository;
import com.example.taskflow.domain.team.repository.TeamUserRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitData {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;

    @PostConstruct
    @Transactional
    public void init() {
        // --- Users ---
        User user1 = new User("alpha", "alpha@test.com", passwordEncoder.encode("1234"), "알파", UserRole.ADMIN);
        User user2 = new User("beta", "beta@test.com", passwordEncoder.encode("1234"), "베타", UserRole.USER);

        userRepository.save(user1);
        userRepository.save(user2);

        // --- Team ---
        Team team1 = new Team("팀1", "");
        teamRepository.save(team1);

        // --- TeamUser ---
        TeamUser teamUser1 = new TeamUser(team1, user1);
        teamUserRepository.save(teamUser1);


        // --- Tasks ---
        LocalDateTime now = LocalDateTime.now();

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

        Task taskPastDone = new Task(
                "과거 완료 작업",
                "이미 끝난 일",
                TaskPriority.LOW,
                user1,
                now.minusDays(5)
        );
        taskPastDone.completedTaskAt(now.minusDays(4));

        Task taskPastTodo = new Task(
                "과거 미완료 작업",
                "아직 안 끝남",
                TaskPriority.MEDIUM,
                user1,
                now.minusDays(3)
        );

        Task taskToday = new Task(
                "오늘 마감 작업",
                "오늘 해야 함",
                TaskPriority.HIGH,
                user1,
                now
        );

        Task taskFuture = new Task(
                "미래 작업",
                "아직 멀었음",
                TaskPriority.LOW,
                user1,
                now.plusDays(3)
        );

        taskRepository.save(taskPastDone);
        taskRepository.save(taskPastTodo);
        taskRepository.save(taskToday);
        taskRepository.save(taskFuture);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
    }

}

