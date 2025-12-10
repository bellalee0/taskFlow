package com.example.taskflow.common.exception;

import com.example.taskflow.common.model.response.JwtErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtExceptionHandler {
    public void handleCustomException(HttpServletResponse response, CustomException e)
            throws IOException {

        response.setStatus(e.getErrorMessage().getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        JwtErrorResponse errorResponse = new JwtErrorResponse(e.getErrorMessage());
        writeErrorResponse(response, errorResponse);
    }

    public void writeErrorResponse(HttpServletResponse response, JwtErrorResponse body)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(body);

        try (PrintWriter writer = response.getWriter()) {
            writer.write(json);
            writer.flush();
        }
    }
}