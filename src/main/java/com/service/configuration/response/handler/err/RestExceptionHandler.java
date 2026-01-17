package com.service.configuration.response.handler.err;

import com.service.configuration.response.body.ApiResponse;
import com.service.configuration.response.handler.err.exception.RestException;
import com.service.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RestExceptionHandler {
    private final ResponseUtil r;

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleAllExceptions(Exception ex) {
        log.error("Catch exception in handleAllExceptions", ex);
        return r.response(null, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(RestException.class)
    public ApiResponse<String> handleResException(RestException ex) {
        log.error("Catch exception in handleResException", ex);
        return r.response(null , ex.getMessage(), ex.getStatus());
    }

}
