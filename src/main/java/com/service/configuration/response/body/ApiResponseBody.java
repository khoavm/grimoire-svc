package com.service.configuration.response.body;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponseBody<T> implements Serializable {
    private T data;
    private String message;
    private Integer statusCode;
}
