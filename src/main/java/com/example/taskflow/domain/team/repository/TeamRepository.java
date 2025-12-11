package com.example.taskflow.domain.team.repository;

import static com.example.taskflow.common.exception.ErrorMessage.TEAM_NOT_FOUND;

import com.example.taskflow.common.entity.Team;
import com.example.taskflow.common.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

    boolean existsByName(String name);

    default Team findTeamById (Long teamId) {
        return findById(teamId)
                .orElseThrow(() -> new CustomException(TEAM_NOT_FOUND));
    }
}
