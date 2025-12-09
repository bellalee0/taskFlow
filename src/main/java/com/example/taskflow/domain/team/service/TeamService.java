package com.example.taskflow.domain.team.service;

import com.example.taskflow.common.entity.Team;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.exception.ErrorMessage;
import com.example.taskflow.domain.team.model.dto.TeamDto;
import com.example.taskflow.domain.team.model.request.TeamCreateRequest;
import com.example.taskflow.domain.team.model.response.TeamCreateResponse;
import com.example.taskflow.domain.team.repository.TeamRepository;
import com.example.taskflow.domain.team.repository.TeamUserRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;
    private final UserRepository userRepository;

    //팀 생성
    @Transactional
    public TeamCreateResponse createTeam(TeamCreateRequest request) {
        if (teamRepository.existsByName(request.getName())) {
            throw new CustomException(ErrorMessage.TEAM_ALREADY_PRESENT);
        }

        Team team = new Team(
                request.getName(),
                request.getDescription()
        );
        teamRepository.save(team);

        TeamDto dto = TeamDto.from(team);
        return TeamCreateResponse.from(dto);
    }


}
