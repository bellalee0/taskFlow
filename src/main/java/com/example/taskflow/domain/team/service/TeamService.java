package com.example.taskflow.domain.team.service;

import com.example.taskflow.domain.team.repository.TeamRepository;
import com.example.taskflow.domain.team.repository.TeamUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;

}
