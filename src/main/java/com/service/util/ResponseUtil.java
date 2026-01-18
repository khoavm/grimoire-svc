package com.service.util;

import com.service.configuration.response.body.ApiResponse;
import lombok.NoArgsConstructor;
import org.hibernate.dialect.PostgreSQLDialect;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ResponseUtil {

    public <T extends Serializable> ApiResponse<T> response(T data, String message, HttpStatusCode statusCode) {
        return new ApiResponse<T>(data, message, statusCode);
    }

    public <T extends Serializable> ApiResponse<T> ok(T data) {
        return new ApiResponse<T>(data, "", HttpStatus.OK);
    }




}
