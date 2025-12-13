package com.example.taskflow.fixture;

import com.example.taskflow.common.entity.Comment;
import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;

public class CommentFixture {

    public static String DEFAULT_CONTENT = "테스트 댓글";

    public static Comment createTestParentComment(User user, Task task) {
        return new Comment(DEFAULT_CONTENT, user, task, null);
    }

    public static Comment createTestChildComment(User user, Task task, Comment parentComment) {
        return new Comment(DEFAULT_CONTENT, user, task, parentComment);
    }
}
