package com.daylog.controller;

import com.daylog.common.CMRespDto;
import com.daylog.request.PostCreate;
import com.daylog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<?> post(@Valid @RequestBody PostCreate postCreate
                                , BindingResult bindingResult) {
        postService.write(postCreate);
        return new ResponseEntity<>(new CMRespDto<>(1, "success", null), HttpStatus.CREATED);
    }

}
