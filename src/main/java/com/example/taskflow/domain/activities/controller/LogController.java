package com.example.taskflow.domain.activities.controller;

import com.example.taskflow.domain.activities.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;
}
