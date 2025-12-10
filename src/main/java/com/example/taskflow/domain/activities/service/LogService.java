package com.example.taskflow.domain.activities.service;

import com.example.taskflow.domain.activities.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
}
