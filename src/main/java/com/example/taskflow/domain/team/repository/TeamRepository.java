package com.example.taskflow.domain.team.repository;

import com.example.taskflow.common.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
