package com.service.configuration.response.handler.err.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class RestException extends RuntimeException {
    private String message;
    private HttpStatus status;
    private Object data;

    public RestException(String message, HttpStatus status, Object data) {
        super(message);
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public static void throwBadRequest(String message) {
        throw BadRequest(message);
    }


    public static void throwNotFound(String message) {
        throw NotFound(message);
    }

    public static void throwConflict(String message) {
        throw new RestException(message, HttpStatus.CONFLICT, null);
    }


    public static RestException BadRequest(String message) {
        return new RestException(message, HttpStatus.BAD_REQUEST, null);
    }

    public static RestException NotFound(String message) {
        return new RestException(message, HttpStatus.NOT_FOUND, null);
    }
}
