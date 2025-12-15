package com.example.taskflow.fixture;

import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.model.enums.TaskPriority;
import java.time.LocalDateTime;

public class TaskFixture {

    public static String DEFAULT_TITLE = "테스트 일정";
    public static String DEFAULT_DESCRIPTION = "테스트 일정 설명";
    public static TaskPriority DEFAULT_PRIORITY = TaskPriority.MEDIUM;
    public static LocalDateTime DEFAULT_DUE_DATE = LocalDateTime.now().plusDays(2);

    public static Task createTestTask(User user) {
        return new Task(DEFAULT_TITLE, DEFAULT_DESCRIPTION, DEFAULT_PRIORITY, user, DEFAULT_DUE_DATE);
    }
}
