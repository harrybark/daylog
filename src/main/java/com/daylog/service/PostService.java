package com.daylog.service;

import com.daylog.domain.Post;
import com.daylog.handler.ex.CustomValidationApiException;
import com.daylog.postResponse.PostResponse;
import com.daylog.repository.PostRepository;
import com.daylog.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Long write(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .contents(postCreate.getContents())
                .build()
                ;
        postRepository.save(post);

        return post.getId();
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomValidationApiException("Not Found post"));
        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .build();

        return postResponse;
    }
}
