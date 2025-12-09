package com.example.taskflow.domain.team.service;

import com.example.taskflow.common.entity.Team;
import com.example.taskflow.common.entity.TeamUser;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.exception.ErrorMessage;
import com.example.taskflow.domain.team.model.dto.TeamDto;
import com.example.taskflow.domain.team.model.request.TeamCreateRequest;
import com.example.taskflow.domain.team.model.request.TeamMemberCreateRequest;
import com.example.taskflow.domain.team.model.request.TeamUpdateRequest;
import com.example.taskflow.domain.team.model.response.TeamCreateResponse;
import com.example.taskflow.domain.team.model.response.TeamMemberCreateResponse;
import com.example.taskflow.domain.team.model.response.TeamUpdateResponse;
import com.example.taskflow.domain.team.repository.TeamRepository;
import com.example.taskflow.domain.team.repository.TeamUserRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;
    private final UserRepository userRepository;

    //region 팀 생성
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
    //endregion

    //region 팀 수정
    @Transactional
    public TeamUpdateResponse updateTeam(TeamUpdateRequest request, Long teamId) {
        Team selectedTeam = teamRepository.findTeamById(teamId);

        String newName = request.getName();
        String oldName = selectedTeam.getName();

        if (!oldName.equals(newName) && teamRepository.existsByName(request.getName())) {
            throw new CustomException(ErrorMessage.TEAM_ALREADY_PRESENT);
        }

        selectedTeam.updateTeam(
                request.getName(),
                request.getDescription()
        );

        TeamDto dto = TeamDto.from(selectedTeam);
        return TeamUpdateResponse.from(dto);
    }
    //endregion

    //region 팀 삭제
    @Transactional
    public void deleteTeam(Long teamId) {
        Team selectedTeam = teamRepository.findTeamById(teamId);
        boolean teamHasUser = teamUserRepository.existsByTeam_Id(teamId);

        if (teamHasUser) {
            throw new CustomException(ErrorMessage.TEAM_HAS_USER_WHEN_DELETE);
        }

        selectedTeam.updateIsDeleted();
    }
    //endregion

    //region 팀 멤버 추가
    public TeamMemberCreateResponse createTeamMember(TeamMemberCreateRequest request, Long teamId) {
        if (teamUserRepository.existsByTeam_IdAndUser_Id(teamId, request.getUserId())){
            throw new CustomException(ErrorMessage.TEAMUSER_ALREADY_PRESENT);
        }

        Team team = teamRepository.findTeamById(teamId);
        User user = userRepository.findUserById(request.getUserId());

        TeamUser teamUser = new TeamUser(team, user);
        teamUserRepository.save(teamUser);

        List<TeamUser> teamUsers= teamUserRepository.findByTeamId(teamId);
        List<TeamMemberCreateResponse.UserInfo> members = new ArrayList<>();
        for (TeamUser dto : teamUsers) {
            members.add(new TeamMemberCreateResponse.UserInfo(
                    dto.getUser().getId(),
                    dto.getUser().getUserName(),
                    dto.getUser().getName()
            ));
        }

        TeamDto teamDto = TeamDto.from(team);

        return TeamMemberCreateResponse.from(teamDto, members);

    }
    //endregion


}
