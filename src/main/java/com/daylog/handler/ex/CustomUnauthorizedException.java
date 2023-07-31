package com.daylog.handler.ex;

import org.springframework.http.HttpStatus;

public class CustomUnauthorizedException extends CustomCommonException {

    // 객체를 구분할 때
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Post Not Found";

    public CustomUnauthorizedException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus statusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}
