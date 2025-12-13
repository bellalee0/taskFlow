package com.example.taskflow.domain.search.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.taskflow.common.entity.Task;
import com.example.taskflow.common.entity.Team;
import com.example.taskflow.common.entity.User;
import com.example.taskflow.domain.search.model.response.SearchResponse;
import com.example.taskflow.domain.task.model.request.TaskCreateRequest;
import com.example.taskflow.domain.task.model.response.TaskCreateResponse;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.team.repository.TeamRepository;
import com.example.taskflow.domain.user.repository.UserRepository;
import com.example.taskflow.fixture.TaskFixture;
import com.example.taskflow.fixture.TeamFixture;
import com.example.taskflow.fixture.UserFixture;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SearchService searchService;

    @Test
    @DisplayName("검색 테스트 - 성공: 유효한 내용 입력")
    void search() {

        // Given
        User user = UserFixture.createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Team team = TeamFixture.createTestTeam();
        ReflectionTestUtils.setField(team, "id", 1L);

        Task task = TaskFixture.createTestTask(user);
        ReflectionTestUtils.setField(task, "id", 1L);

        String keyword = "테스트";

        when(taskRepository.searchByKeyword(anyString())).thenReturn(List.of(task));
        when(teamRepository.searchByKeyword(anyString())).thenReturn(List.of(team));
        when(userRepository.searchByKeyword(anyString())).thenReturn(List.of());

        // When
        SearchResponse response = searchService.search(keyword);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getTasks()).hasSize(1);
        assertThat(response.getTasks().get(0).getTitle()).contains(task.getTitle());
        assertThat(response.getTeams()).hasSize(1);
        assertThat(response.getTeams().get(0).getName()).contains(team.getName());
        assertThat(response.getUsers()).hasSize(0);
    }
}