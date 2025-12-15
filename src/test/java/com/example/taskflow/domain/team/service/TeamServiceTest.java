package com.example.taskflow.domain.team.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.taskflow.common.entity.Team;
import com.example.taskflow.common.entity.TeamUser;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.domain.team.model.request.TeamCreateRequest;
import com.example.taskflow.domain.team.model.request.TeamMemberCreateRequest;
import com.example.taskflow.domain.team.model.request.TeamUpdateRequest;
import com.example.taskflow.domain.team.model.response.TeamCreateResponse;
import com.example.taskflow.domain.team.model.response.TeamGetListResponse;
import com.example.taskflow.domain.team.model.response.TeamGetMemberResponse;
import com.example.taskflow.domain.team.model.response.TeamGetOneResponse;
import com.example.taskflow.domain.team.model.response.TeamMemberCreateResponse;
import com.example.taskflow.domain.team.model.response.TeamUpdateResponse;
import com.example.taskflow.domain.team.repository.TeamRepository;
import com.example.taskflow.domain.team.repository.TeamUserRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import com.example.taskflow.fixture.TeamFixture;
import com.example.taskflow.fixture.UserFixture;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamUserRepository teamUserRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TeamService teamService;

    @Test
    @DisplayName("팀 목록 조회 테스트 - 성공")
    void getTeamList_success() {

        // Given
        Team team = TeamFixture.createTestTeam();
        ReflectionTestUtils.setField(team, "id", 1L);

        List<Team> teamList = new ArrayList<>();
        teamList.add(team);

        when(teamRepository.findAll()).thenReturn(teamList);

        // When
        List<TeamGetListResponse> response = teamService.getTeamList();

        // Then
        assertThat(response).isNotNull();
        assertThat(response).hasSize(1);
        assertThat(response.get(0).getName()).isEqualTo(team.getName());
    }

    @Test
    @DisplayName("팀 상세 조회 테스트 - 성공")
    void getTeamOne_success() {

        // Given
        Team team = TeamFixture.createTestTeam();
        ReflectionTestUtils.setField(team, "id", 1L);

        when(teamRepository.findTeamById(anyLong())).thenReturn(team);

        // When
        TeamGetOneResponse response = teamService.getTeamOne(1L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(team.getName());
    }

    @Test
    @DisplayName("팀 멤버 조회 테스트 - 성공")
    void getTeamMember_success() {

        // Given
        Team team = TeamFixture.createTestTeam();
        ReflectionTestUtils.setField(team, "id", 1L);

        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        TeamUser teamUser = new TeamUser(team, user);
        ReflectionTestUtils.setField(teamUser, "id", 1L);

        List<TeamUser> teamUserList = new ArrayList<>();
        teamUserList.add(teamUser);

        when(teamRepository.existsById(anyLong())).thenReturn(true);
        when(teamUserRepository.findByTeamId(anyLong())).thenReturn(teamUserList);

        // When
        List<TeamGetMemberResponse> response = teamService.getTeamMember(1L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response).hasSize(1);
        assertThat(response.get(0).getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("팀 멤버 조회 테스트 - 실패: 존재하지 않는 팀 ID")
    void getTeamMember_failure() {

        // Given
        when(teamRepository.existsById(anyLong())).thenReturn(false);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> teamService.getTeamMember(1L));
        assertEquals("팀을 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("팀 생성 테스트 - 성공")
    void createTeam_success() {

        // Given
        Team team = TeamFixture.createTestTeam();
        ReflectionTestUtils.setField(team, "id", 1L);

        TeamCreateRequest request = new TeamCreateRequest();
        ReflectionTestUtils.setField(request, "name", TeamFixture.DEFAULT_TEAMNAME);
        ReflectionTestUtils.setField(request, "description", TeamFixture.DEFAULT_DESCRIPTION);

        when(teamRepository.existsByName(anyString())).thenReturn(false);

        // When
        TeamCreateResponse response = teamService.createTeam(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(team.getName());
        assertThat(response.getDescription()).isEqualTo(team.getDescription());

        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    @DisplayName("팀 생성 테스트 - 실패: 이미 존재하는 팀 이름")
    void createTeam_failure() {

        // Given
        Team team = TeamFixture.createTestTeam();
        ReflectionTestUtils.setField(team, "id", 1L);

        TeamCreateRequest request = new TeamCreateRequest();
        ReflectionTestUtils.setField(request, "name", TeamFixture.DEFAULT_TEAMNAME);
        ReflectionTestUtils.setField(request, "description", TeamFixture.DEFAULT_DESCRIPTION);

        when(teamRepository.existsByName(anyString())).thenReturn(true);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> teamService.createTeam(request));
        assertEquals("이미 존재하는 팀 이름입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("팀 수정 테스트 - 성공")
    void updateTeam_success() {

        // Given
        Team team = TeamFixture.createTestTeam();
        ReflectionTestUtils.setField(team, "id", 1L);

        TeamUpdateRequest request = new TeamUpdateRequest();
        ReflectionTestUtils.setField(request, "name", "팀 이름 수정");
        ReflectionTestUtils.setField(request, "description", "팀 설명 수정");

        when(teamRepository.findTeamById(anyLong())).thenReturn(team);
        when(teamRepository.existsByName(anyString())).thenReturn(false);

        // When
        TeamUpdateResponse response = teamService.updateTeam(request, 1L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("팀 이름 수정");
        assertThat(response.getDescription()).isEqualTo("팀 설명 수정");
    }

    @Test
    @DisplayName("팀 수정 테스트 - 실패: 이미 존재하는 팀 이름")
    void updateTeam_failure() {

        // Given
        Team team = TeamFixture.createTestTeam();
        ReflectionTestUtils.setField(team, "id", 1L);

        TeamUpdateRequest request = new TeamUpdateRequest();
        ReflectionTestUtils.setField(request, "name", "중복된 팀 이름");
        ReflectionTestUtils.setField(request, "description", "팀 설명 수정");

        when(teamRepository.findTeamById(anyLong())).thenReturn(team);
        when(teamRepository.existsByName(anyString())).thenReturn(true);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> teamService.updateTeam(request, 1L));
        assertEquals("이미 존재하는 팀 이름입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("팀 삭제 테스트 - 성공")
    void deleteTeam_success() {

        // Given
        Team team = TeamFixture.createTestTeam();
        ReflectionTestUtils.setField(team, "id", 1L);

        when(teamRepository.findTeamById(anyLong())).thenReturn(team);
        when(teamUserRepository.existsByTeamId(anyLong())).thenReturn(false);

        // When & Then
        teamService.deleteTeam(1L);
    }

    @Test
    @DisplayName("팀 삭제 테스트 - 실패: 팀에 멤버가 존재함")
    void deleteTeam_failure() {

        // Given
        Team team = TeamFixture.createTestTeam();
        ReflectionTestUtils.setField(team, "id", 1L);

        when(teamRepository.findTeamById(anyLong())).thenReturn(team);
        when(teamUserRepository.existsByTeamId(anyLong())).thenReturn(true);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> teamService.deleteTeam(1L));
        assertEquals("팀에 멤버가 존재하여 삭제할 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("팀 멤버 추가 테스트 - 성공")
    void createTeamMember_success() {

        // Given
        Team team = TeamFixture.createTestTeam();
        ReflectionTestUtils.setField(team, "id", 1L);

        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        TeamMemberCreateRequest request = new TeamMemberCreateRequest();
        ReflectionTestUtils.setField(request, "userId", 1L);

        TeamUser teamUser = new TeamUser(team, user);
        List<TeamUser> teamUserList = new ArrayList<>();
        teamUserList.add(teamUser);

        when(teamUserRepository.existsByTeamIdAndUserId(anyLong(), anyLong())).thenReturn(false);
        when(teamRepository.findTeamById(anyLong())).thenReturn(team);
        when(userRepository.findUserById(anyLong())).thenReturn(user);
        when(teamUserRepository.findByTeamId(anyLong())).thenReturn(teamUserList);

        // When
        TeamMemberCreateResponse response = teamService.createTeamMember(request, 1L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(team.getName());
        assertThat(response.getDescription()).isEqualTo(team.getDescription());
        assertThat(response.getMembers().size()).isEqualTo(1L);
        assertThat(response.getMembers().get(0).getUsername()).isEqualTo(user.getUsername());

        verify(teamUserRepository, times(1)).save(any(TeamUser.class));
    }

    @Test
    @DisplayName("팀 멤버 추가 테스트 - 실패: 이미 팀에 등록된 사용자")
    void createTeamMember_failure() {

        // Given
        Team team = TeamFixture.createTestTeam();
        ReflectionTestUtils.setField(team, "id", 1L);

        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        TeamMemberCreateRequest request = new TeamMemberCreateRequest();
        ReflectionTestUtils.setField(request, "userId", 1L);

        when(teamUserRepository.existsByTeamIdAndUserId(anyLong(), anyLong())).thenReturn(true);

        // When & Then
        CustomException exception = assertThrows(CustomException.class,
            () -> teamService.createTeamMember(request, 1L));
        assertEquals("이미 팀에 속한 멤버입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("팀 멤버 제거 테스트 - 성공")
    void deleteTeamMember_success() {

        // Given
        Team team = TeamFixture.createTestTeam();
        ReflectionTestUtils.setField(team, "id", 1L);

        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        TeamUser teamUser = new TeamUser(team, user);

        when(teamUserRepository.findTeamUserOrElseThrow(anyLong(), anyLong())).thenReturn(teamUser);

        // When & Then
        teamService.deleteTeamMember(1L, 1L);
    }
}