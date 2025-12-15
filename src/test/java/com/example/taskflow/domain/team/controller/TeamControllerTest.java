package com.example.taskflow.domain.team.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taskflow.common.entity.Team;
import com.example.taskflow.common.entity.TeamUser;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.common.utils.JwtUtil;
import com.example.taskflow.domain.team.repository.TeamRepository;
import com.example.taskflow.domain.team.repository.TeamUserRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import com.example.taskflow.fixture.TeamFixture;
import com.example.taskflow.fixture.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamUserRepository teamUserRepository;

    @Autowired
    private JwtUtil jwtUtil;

    User user;
    String token;
    Team team;

    @BeforeEach
    void setUp() {

        user = UserFixture.createTestUser();
        userRepository.save(user);

        token = "Bearer " + jwtUtil.generationToken(user.getId(), user.getUsername());

        team = TeamFixture.createTestTeam();
        teamRepository.save(team);
    }

    @Test
    @DisplayName("GET /api/teams 통합 테스트 - 성공: 팀 목록 조회 성공")
    void getTeamListApi() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/teams/{id} 통합 테스트 - 성공: 팀 상세 조회 성공")
    void getTeamOneApi() throws Exception {

        // Given
        long id = team.getId();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/teams/%d", id))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/teams/{teamId}/members 통합 테스트 - 성공: 팀 멤버 조회 성공")
    void getTeamMemberApi() throws Exception {

        // Given
        long id = team.getId();

        TeamUser teamUser = new TeamUser(team, user);
        teamUserRepository.save(teamUser);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/teams/%d/members", id))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/teams 통합 테스트 - 성공: 팀 생성 성공")
    void createTeamApi() throws Exception {

        // Given
        String requestBody =
            """
            {
                "name": "테스트 팀 이름",
                "description": "테스트 용으로 생성한 팀"
            }
            """;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(requestBody))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PUT /api/teams/{id} 통합 테스트 - 성공: 팀 수정 성공")
    void updateTeamApi() throws Exception {

        // Given
        long id = team.getId();

        String requestBody =
            """
            {
                "name": "팀 이름 수정",
                "description": "내용 수정"
            }
            """;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put(String.format("/api/teams/%d", id))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/teams/{id} 통합 테스트 - 성공: 팀 삭제 성공")
    void deleteTeamApi() throws Exception {

        // Given
        long id = team.getId();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/api/teams/%d", id))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/teams/{teamId}/members 통합 테스트 - 성공: 팀 멤버 추가 성공")
    void createTeamMemberApi() throws Exception {

        // Given
        long id = team.getId();
        long userId = user.getId();

        String requestBody = String.format(
            """
            {
                "userId": "%d"
            }
            """, userId);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post(String.format("/api/teams/%d/members", id))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/teams/{teamId}/members/{userId} 통합 테스트 - 성공: 팀 멤버 제거 성공")
    void deleteTeamMemberApi() throws Exception {

        // Given
        long id = team.getId();
        long userId = user.getId();

        TeamUser teamUser = new TeamUser(team, user);
        teamUserRepository.save(teamUser);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/api/teams/%d/members/%d", id, userId))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
            .andExpect(status().isOk());
    }
}