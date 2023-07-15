package com.daylog.handler.ex;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomPostNotFoundException extends CustomCommonException {

    // 객체를 구분할 때
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Post Not Found";

    public CustomPostNotFoundException(String message) {
        super(MESSAGE);
    }

    @Override
    public HttpStatus statusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
