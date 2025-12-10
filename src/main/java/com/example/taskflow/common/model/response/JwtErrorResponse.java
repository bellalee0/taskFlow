package com.example.taskflow.common.model.response;

import com.example.taskflow.common.exception.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class JwtErrorResponse {
    private HttpStatus status;
    private String message;

    public JwtErrorResponse(ErrorMessage errorMessage) {
        this.status = errorMessage.getStatus();
        this.message = errorMessage.getMessage();
    }
}
