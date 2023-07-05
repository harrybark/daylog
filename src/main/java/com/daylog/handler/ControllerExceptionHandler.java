package com.daylog.handler;

import com.daylog.common.CMRespDto;
import com.daylog.handler.exception.CustomValidationApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<CMRespDto<?>> validationException(CustomValidationApiException e){
        return new ResponseEntity<>(new CMRespDto<>(0, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }
}