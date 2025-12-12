package com.example.taskflow.domain.search.model.response;

import com.example.taskflow.domain.search.model.dto.TaskSearchDto;
import com.example.taskflow.domain.search.model.dto.TeamSearchDto;
import com.example.taskflow.domain.search.model.dto.UserSearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchResponse {

    private List<TaskSearchDto> tasks;
    private List<TeamSearchDto> teams;
    private List<UserSearchDto> users;
}
