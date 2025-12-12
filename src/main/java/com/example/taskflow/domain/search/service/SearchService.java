package com.example.taskflow.domain.search.service;

import com.example.taskflow.domain.search.model.dto.TaskSearchDto;
import com.example.taskflow.domain.search.model.dto.TeamSearchDto;
import com.example.taskflow.domain.search.model.dto.UserSearchDto;
import com.example.taskflow.domain.search.model.response.SearchResponse;
import com.example.taskflow.domain.task.model.dto.TaskDto;
import com.example.taskflow.domain.task.repository.TaskRepository;
import com.example.taskflow.domain.team.model.dto.TeamDto;
import com.example.taskflow.domain.team.repository.TeamRepository;
import com.example.taskflow.domain.user.model.dto.UserDto;
import com.example.taskflow.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public SearchResponse search(String keyword) {

        List<TaskSearchDto> tasks = taskRepository.searchByKeyword(keyword)
                .stream()
                .map(TaskDto::from)
                .map(TaskSearchDto::from)
                .toList();

        List<TeamSearchDto> teams = teamRepository.searchByKeyword(keyword)
                .stream()
                .map(TeamDto::from)
                .map(TeamSearchDto::from)
                .toList();

        List<UserSearchDto> users = userRepository.searchByKeyword(keyword)
                .stream()
                .map(UserDto::from)
                .map(UserSearchDto::from)
                .toList();

        return SearchResponse.from(tasks, teams, users);
    }
}

