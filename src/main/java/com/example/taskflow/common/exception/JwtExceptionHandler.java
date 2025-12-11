package com.example.taskflow.common.exception;

import com.example.taskflow.common.model.response.GlobalResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class JwtExceptionHandler {

    public void handleCustomException(HttpServletResponse response, CustomException e)
            throws IOException {

        response.setStatus(e.getErrorMessage().getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        writeErrorResponse(response, GlobalResponse.exception("인증이 필요합니다."));
    }

    public void writeErrorResponse(HttpServletResponse response, GlobalResponse<Void> body)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = objectMapper.writeValueAsString(body);

        try (PrintWriter writer = response.getWriter()) {
            writer.write(json);
            writer.flush();
        }
    }
}