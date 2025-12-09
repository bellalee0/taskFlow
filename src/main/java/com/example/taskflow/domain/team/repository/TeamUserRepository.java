package com.example.taskflow.domain.team.repository;

import com.example.taskflow.common.entity.TeamUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {
}
