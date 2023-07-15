package com.daylog.handler.ex;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class CustomCommonException extends RuntimeException {

    private String message;
    private final Map<String, String> errorMap = new HashMap<>();

    public CustomCommonException() {
    }
    public CustomCommonException(String message) {
        this.message = message;
    }

    public void addValidation(String fieldName, String message) {
        errorMap.put(fieldName, message);
    }

    public abstract HttpStatus statusCode();
}
