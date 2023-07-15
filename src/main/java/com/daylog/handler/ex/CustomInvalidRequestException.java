package com.daylog.handler.ex;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomInvalidRequestException extends CustomCommonException {
    // 객체를 구분할 때
    private static final long serialVersionUID = 1L;

    private String fieldName;
    private static final String MESSAGE = "Invalid Request";

    public CustomInvalidRequestException(String message) {
        super(MESSAGE);
    }

    public CustomInvalidRequestException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public HttpStatus statusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
