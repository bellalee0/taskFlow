package com.example.taskflow.domain.activities.repository;

import com.example.taskflow.common.entity.Log;
import com.example.taskflow.common.model.enums.LogType;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LogRepository extends JpaRepository<Log, Long> {

    @Query("""
            SELECT l
            FROM Log l
            WHERE (:type IS NULL OR l.type = :type)
            AND (:taskId IS NULL OR l.task.id = :taskId)
            AND (:startDate IS NULL OR l.loggedDateTime >= :startDate)
            AND (:endDate IS NULL OR l.loggedDateTime <= :endDate)
            """)
    Page<Log> findByFilters(
        Pageable pageable,
        @Param("type") LogType type,
        @Param("taskId") Long taskId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate);
}
