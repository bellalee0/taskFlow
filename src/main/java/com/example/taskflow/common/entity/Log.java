package com.example.taskflow.common.entity;

import com.example.taskflow.common.model.enums.LogType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LogType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(nullable = false)
    private LocalDateTime timestamp;

//    @Column(nullable = false)
    private String description;


    public Log(LogType type, User user, Task task, String description) {
        this.type = type;
        this.user = user;
        this.task = task;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }

    public Log(LogType type, User user, Comment comment, String description) {
        this.type = type;
        this.user = user;
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }
}
