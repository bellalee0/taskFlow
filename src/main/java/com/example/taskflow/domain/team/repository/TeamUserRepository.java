package com.example.taskflow.domain.team.repository;

import static com.example.taskflow.common.exception.ErrorMessage.TEAMUSER_NOT_FOUND;

import com.example.taskflow.common.entity.TeamUser;
import com.example.taskflow.common.exception.CustomException;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {

    boolean existsByTeamId(Long teamId);

    boolean existsByTeamIdAndUserId(Long teamId, Long userId);

    @EntityGraph(attributePaths = "user")
    List<TeamUser> findByTeamId(Long teamId);

    Optional<TeamUser> findByTeam_IdAndUser_Id(Long teamId, Long userId);

    default TeamUser findTeamUserOrElseThrow(Long teamId, Long userId) {
        return findByTeam_IdAndUser_Id(teamId, userId)
                .orElseThrow(()-> new CustomException(TEAMUSER_NOT_FOUND));
    }
}
