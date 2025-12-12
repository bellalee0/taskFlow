package com.example.taskflow.domain.task.repository;

import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.model.enums.TaskPriority;
import com.example.taskflow.common.model.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.example.taskflow.common.exception.ErrorMessage.TASK_NOT_FOUND;

import java.time.LocalDateTime;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // 다중 필터 작업 목록 조회 (상태, 우선순위, 담당자)
    @EntityGraph(attributePaths = {"assigneeId"})
    @Query("""
            SELECT t FROM Task t WHERE
            (:status IS NULL OR t.status = :status) AND
            (:priority IS NULL OR t.priority = :priority) AND
            (:assigneeId IS NULL OR t.assigneeId.id = :assigneeId)
            """)
    Page<Task> findByFilters(
            @Param("status") TaskStatus status,
            @Param("priority") TaskPriority priority,
            @Param("assigneeId") Long assigneeId,
            Pageable pageable);

    List<Task> findAllByAssigneeId(User assigneeId);

    Long countByStatus(TaskStatus taskStatus);

    Long countByStatusNotAndDueDateBefore(TaskStatus taskStatus, LocalDateTime now);

    Long countByAssigneeIdId(Long userId);

    Long countByAssigneeIdIdAndStatus(Long userId, TaskStatus taskStatus);

    List<Task> findByAssigneeIdIdAndDueDateBetween(Long assigneeId, LocalDateTime start, LocalDateTime end);

    List<Task> findByAssigneeIdIdAndDueDateAfterAndStatusNot(Long assigneeId, LocalDateTime today, TaskStatus status);

    List<Task> findByAssigneeIdIdAndDueDateBeforeAndStatusNot(Long assigneeId, LocalDateTime today, TaskStatus status);

    Long countByCompletedDateTimeBetween(LocalDateTime startOfToday, LocalDateTime endOfToday);

    Long countByDueDateLessThan(LocalDateTime end);

    Long countByCompletedDateTimeLessThan(LocalDateTime end);

    @Query("SELECT t FROM Task t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Task> searchByKeyword(@Param("keyword") String keyword);

    default Task findTaskById(Long taskId) {
        return findById(taskId)
            .orElseThrow(() -> new CustomException(TASK_NOT_FOUND));
    }
}
