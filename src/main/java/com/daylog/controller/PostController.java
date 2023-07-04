package com.daylog.controller;

import com.daylog.common.CMRespDto;
import com.daylog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class PostController {

    @PostMapping("/posts")
    public ResponseEntity<?> post(@Valid @RequestBody PostCreate postCreate, BindingResult bindingResult) {
        log.info("PostCreate = {}", postCreate);

        return new ResponseEntity<>(new CMRespDto<>(1, "success", null), HttpStatus.CREATED);
    }

}
