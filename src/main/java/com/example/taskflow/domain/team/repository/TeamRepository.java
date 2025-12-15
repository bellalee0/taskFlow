package com.example.taskflow.domain.team.repository;

import com.example.taskflow.common.entity.Team;
import com.example.taskflow.common.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.example.taskflow.common.exception.ErrorMessage.TEAM_NOT_FOUND;

public interface TeamRepository extends JpaRepository<Team, Long> {

    boolean existsByName(String name);

    default Team findTeamById (Long teamId) {
        return findById(teamId)
                .orElseThrow(() -> new CustomException(TEAM_NOT_FOUND));
    }

    @Query("SELECT t FROM Team t WHERE " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Team> searchByKeyword(@Param("keyword") String keyword);
}
