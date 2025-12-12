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

    private final List<TaskSearchDto> tasks;
    private final List<TeamSearchDto> teams;
    private final List<UserSearchDto> users;

    public static SearchResponse from(List<TaskSearchDto> taskSearchDto, List<TeamSearchDto> teamSearchDto, List<UserSearchDto> userSearchDto) {
        return new SearchResponse(
               taskSearchDto,
               teamSearchDto,
               userSearchDto
        );
    }
}
