package com.example.taskflow.domain.activities.repository;

import com.example.taskflow.common.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {

}
