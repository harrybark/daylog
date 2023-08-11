package com.daylog.controller;

import com.daylog.common.CMRespDto;
import com.daylog.request.PostCreate;
import com.daylog.request.PostEdit;
import com.daylog.request.PostSearch;
import com.daylog.response.PostResponse;
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
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/foo")
    public String foo() {
        return "foo";
    }

    @PostMapping("/posts")
    public ResponseEntity<?> post(@Valid @RequestBody PostCreate postCreate
                                , BindingResult bindingResult
    ) {
        postCreate.validate();
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

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<?> update(@PathVariable("postId") Long id
                                    , @Valid @RequestBody PostEdit postEdit) {
        PostResponse postResponse = postService.edit(id, postEdit);
        return new ResponseEntity<>(new CMRespDto<>(1, "success", postResponse), HttpStatus.OK);
    }

    @GetMapping("/posts-all")
    public ResponseEntity<?> get(Pageable pageable) {
        Page<PostResponse> posts = postService.getList(pageable);

        return new ResponseEntity<>(new CMRespDto<>(1, "success", posts), HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getList(PostSearch postSearch) {
        List<PostResponse> posts = postService.getListAll(postSearch);

        return new ResponseEntity<>(new CMRespDto<>(1, "success", posts), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> delete(@PathVariable("postId") Long id) {
        postService.delete(id);
        return new ResponseEntity<>(new CMRespDto<>(1, "success", null), HttpStatus.OK);
    }
}
