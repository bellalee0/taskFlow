package com.example.taskflow.domain.team.service;

import static com.example.taskflow.common.exception.ErrorMessage.*;

import com.example.taskflow.common.entity.Team;
import com.example.taskflow.common.entity.TeamUser;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.domain.team.model.dto.MemberInfoDto;
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
    @Transactional(readOnly = true)
    public List<TeamGetListResponse> getTeamList() {

        List<Team> teamList = teamRepository.findAll();

        List<TeamGetListResponse> teamDtoList = new ArrayList<>();
        for (Team team : teamList) {
            TeamDto teamDto = TeamDto.from(team);
            List<MemberInfoDto> memberDtoList = getMembersInfo(team.getId());

            teamDtoList.add(TeamGetListResponse.from(teamDto, memberDtoList));
        }
        return teamDtoList;
    }
//endregion

    //region 팀 상세 조회
    @Transactional(readOnly = true)
    public TeamGetOneResponse getTeamOne(Long teamId) {

        Team team = teamRepository.findTeamById(teamId);
        TeamDto teamDto = TeamDto.from(team);

        List<MemberInfoDto> memberDtoList = getMembersInfo(teamId);

        return TeamGetOneResponse.from(teamDto, memberDtoList);
    }
    //endregion

    //region 팀 멤버 조회
    @Transactional(readOnly = true)
    public List<TeamGetMemberResponse> getTeamMember(Long teamId) {

        if (!teamRepository.existsById(teamId)) {
            throw new CustomException(TEAM_NOT_FOUND);
        }

        List<TeamUser> teamUserList = teamUserRepository.findByTeamId(teamId);

        return teamUserList.stream().map(teamUser -> TeamGetMemberResponse.from(UserDto.from(teamUser.getUser()))).toList();
    }
    //endregion

    //region 팀 생성
    @Transactional
    public TeamCreateResponse createTeam(TeamCreateRequest request) {

        if (teamRepository.existsByName(request.getName())) {
            throw new CustomException(TEAM_ALREADY_PRESENT);
        }

        Team team = new Team(request.getName(), request.getDescription());
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
            throw new CustomException(TEAM_ALREADY_PRESENT);
        }

        selectedTeam.updateTeam(request);
        teamRepository.saveAndFlush(selectedTeam);

        List<MemberInfoDto> members = getMembersInfo(teamId);

        return TeamUpdateResponse.from(TeamDto.from(selectedTeam), members);
    }
    //endregion

    //region 팀 삭제
    @Transactional
    public void deleteTeam(Long teamId) {

        Team selectedTeam = teamRepository.findTeamById(teamId);

        if (teamUserRepository.existsByTeamId(teamId)) {
            throw new CustomException(TEAM_HAS_USER_WHEN_DELETE);
        }

        selectedTeam.updateIsDeleted();
    }
    //endregion

    //region 팀 멤버 추가
    @Transactional
    public TeamMemberCreateResponse createTeamMember(TeamMemberCreateRequest request, Long teamId) {

        Long userId = request.getUserId();

        if (teamUserRepository.existsByTeamIdAndUserId(teamId, userId)) {
            throw new CustomException(TEAMUSER_ALREADY_PRESENCE);
        }

        Team team = teamRepository.findTeamById(teamId);
        User user = userRepository.findUserById(userId);

        TeamUser teamUser = new TeamUser(team, user);
        teamUserRepository.save(teamUser);

        List<TeamUser> teamUserList = teamUserRepository.findByTeamId(teamId);

        List<MemberInfoDto> memberList = teamUserList.stream()
                .map(savedTeamUser -> MemberInfoDto.from(UserDto.from(savedTeamUser.getUser())))
                .toList();

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

    //List<TeamUser>에서 teamId를 갖는 MembersInfo를 조회하는 매서드
    private List<MemberInfoDto> getMembersInfo(Long teamId) {
        List<TeamUser> teamUsers = teamUserRepository.findByTeamId(teamId);
        return teamUsers.stream()
                        .map(teamUser -> MemberInfoDto.from(UserDto.from(teamUser.getUser())))
                        .toList();
    }
}
