package com.example.taskflow.common.entity;

import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Table(name = "tasks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    //@Column(nullable = false)
    private TaskStatus status;

    //@Column(nullable = false)
    private TaskPriority priority;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assigneeId;

    //@Column(nullable = false)
    private LocalDateTime dueDateTime;

    @ColumnDefault("0")
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isCompleted;

    private LocalDateTime completedDateTime;


    public Task(String title, String description, TaskPriority priority, User assigneeId, LocalDateTime dueDateTime) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.priority = priority;
        this.assigneeId = assigneeId;
        this.dueDateTime = dueDateTime;
        this.isCompleted = false;
        this.completedDateTime = null;
    }

    public void updateStatus(TaskStatus status) {
        this.status = status;
    }
}
