package com.example.taskflow.common.model.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> content = new ArrayList<>();
    private long totalElements;
    private long totalPages;
    private int size;
    private int number;

    public static <T> PageResponse<T> from(Page<T> response) {
        return new PageResponse<>(
            response.getContent(),
            response.getTotalElements(),
            response.getTotalPages(),
            response.getSize(),
            response.getNumber());
    }
}
