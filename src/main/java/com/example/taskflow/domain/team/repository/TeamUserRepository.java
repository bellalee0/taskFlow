package com.example.taskflow.domain.team.repository;

import com.example.taskflow.common.entity.TeamUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {
    boolean existsByTeam_Id(Long teamId);

    boolean existsByTeam_IdAndUser_Id(Long teamId, Long userId);

    @EntityGraph(attributePaths = "user")
    List<TeamUser> findByTeamId(Long teamId);

}
