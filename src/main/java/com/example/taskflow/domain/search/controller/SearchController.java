package com.example.taskflow.domain.search.controller;

import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.model.enums.SuccessMessage;
import com.example.taskflow.common.model.response.GlobalResponse;
import com.example.taskflow.domain.search.model.response.SearchResponse;
import com.example.taskflow.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.taskflow.common.exception.ErrorMessage.SEARCH_REQUIRED_FIELD;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<GlobalResponse<SearchResponse>> search(
            @RequestParam String query
    ) {
        if (query == null || query.trim().isEmpty()) {
            throw new CustomException(SEARCH_REQUIRED_FIELD);
        }

        SearchResponse result = searchService.search(query.trim());

        return ResponseEntity.ok(GlobalResponse.success(SuccessMessage.SEARCH_GET_SUCCESS, result));
    }
}
