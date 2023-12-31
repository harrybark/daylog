package com.daylog.handler;

import com.daylog.common.CMRespDto;
import com.daylog.handler.ex.CustomCommonException;
import com.daylog.handler.ex.CustomValidationApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<CMRespDto<?>> methodNotArgumentValidExceptionHandler(CustomValidationApiException e) {
        return new ResponseEntity<>(new CMRespDto<>(0, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomCommonException.class)
    public ResponseEntity<CMRespDto<?>> apiException(CustomCommonException e){

        return new ResponseEntity<>(new CMRespDto<>(0, e.getMessage(), e.getErrorMap()), e.statusCode());
    }

}
