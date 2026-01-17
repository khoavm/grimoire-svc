package com.service.configuration.response.body;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;


public class ApiResponse<T extends Serializable> extends ResponseEntity<ApiResponseBody<T>> {

    public ApiResponse(T data, String message, HttpStatusCode statusCode) {
        super(new ApiResponseBody<>(data, message, statusCode.value()) ,statusCode);

    }




}