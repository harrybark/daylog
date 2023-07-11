package com.daylog.controller;

import com.daylog.common.CMRespDto;
import com.daylog.domain.Post;
import com.daylog.postResponse.PostResponse;
import com.daylog.request.PostCreate;
import com.daylog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    /**
     * /posts -> findAll(검색 + 페이징)
     * /posts/{postId} -> 글 1개만 조회
     */
    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> get(@PathVariable("postId") Long id) {
        PostResponse postResponse = postService.get(id);
        return new ResponseEntity<>(new CMRespDto<>(1, "success", postResponse), HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> get(Pageable pageable) {
        Page<PostResponse> posts = postService.getAll(pageable);

        return new ResponseEntity<>(new CMRespDto<>(1, "success", posts.getContent()), HttpStatus.OK);
    }
}
