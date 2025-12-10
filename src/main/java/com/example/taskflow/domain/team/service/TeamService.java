package com.example.taskflow.domain.team.service;

import com.example.taskflow.common.entity.Team;
import com.example.taskflow.common.entity.TeamUser;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.exception.ErrorMessage;
import com.example.taskflow.domain.team.model.dto.TeamDto;
import com.example.taskflow.domain.team.model.request.*;
import com.example.taskflow.domain.team.model.response.*;
import com.example.taskflow.domain.team.repository.TeamRepository;
import com.example.taskflow.domain.team.repository.TeamUserRepository;
import com.example.taskflow.domain.user.model.dto.UserDto;
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

    //region 팀 목록 조회
/*    @Transactional(readOnly = true)
    public TeamGetListResponse getTeam () {
       List<Team> teams = teamRepository.findAll();

       for (Team team : teams) {



       }
        TeamDto dto = TeamDto.from(team);
       return TeamGetListResponse.from(dto);
    }*/

    //endregion

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

        return TeamCreateResponse.from(TeamDto.from(team));
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

        return TeamUpdateResponse.from(TeamDto.from(selectedTeam));
    }
    //endregion

    //region 팀 삭제
    @Transactional
    public void deleteTeam(Long teamId) {

        Team selectedTeam = teamRepository.findTeamById(teamId);

        if (teamUserRepository.existsByTeamId(teamId)) {
            throw new CustomException(ErrorMessage.TEAM_HAS_USER_WHEN_DELETE);
        }

        selectedTeam.updateIsDeleted();
    }
    //endregion

    //region 팀 멤버 추가
    @Transactional
    public TeamMemberCreateResponse createTeamMember(TeamMemberCreateRequest request, Long teamId) {

        Long userId = request.getUserId();

        if (teamUserRepository.existsByTeamIdAndUserId(teamId, userId)) {
            throw new CustomException(ErrorMessage.TEAMUSER_ALREADY_PRESENCE);
        }
        Team team = teamRepository.findTeamById(teamId);
        User user = userRepository.findUserById(userId);

        TeamUser teamUser = new TeamUser(team, user);
        teamUserRepository.save(teamUser);

        List<TeamUser> teamUserList = teamUserRepository.findByTeamId(teamId);
        List<MemberIdUsernameNameResponse> memberList = new ArrayList<>();
        for (TeamUser dto : teamUserList) {
            UserDto userDto = UserDto.from(dto.getUser());
            memberList.add(MemberIdUsernameNameResponse.from(userDto));
        }

        return TeamMemberCreateResponse.from(TeamDto.from(team), memberList);
    }
    //endregion

    //region 팀 멤버 제거
    @Transactional
    public void deleteTeamMember(Long teamId, Long userId) {
        TeamUser teamUser = teamUserRepository.findTeamUserOrElseThrow(teamId, userId);
        teamUser.updateIsDeleted();
    }
    //endregion
}
