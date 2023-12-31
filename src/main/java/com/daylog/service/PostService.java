package com.daylog.service;

import com.daylog.domain.Post;
import com.daylog.domain.PostEditor;
import com.daylog.handler.ex.CustomPostNotFoundException;
import com.daylog.response.PostResponse;
import com.daylog.repository.PostRepository;
import com.daylog.request.PostCreate;
import com.daylog.request.PostEdit;
import com.daylog.request.PostSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

        private final PostRepository postRepository;

        @Transactional
        public Long write(PostCreate postCreate) {
                Post post = Post.builder()
                .title(postCreate.getTitle())
                .contents(postCreate.getContents())
                .build();

                postRepository.save(post);

                return post.getId();
        }

        public PostResponse get(Long id) {

                Post post = postRepository.findById(id).orElseThrow(() -> new CustomPostNotFoundException());
                PostResponse postResponse = PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .contents(post.getContents())
                        .build();

                return postResponse;
        }

        public Page<PostResponse> getList(Pageable pageable) {
                return new PageImpl<>(postRepository.findAll(pageable).stream().map(PostResponse::new).collect(Collectors.toList()), pageable, postRepository.count());
        }

        public List<PostResponse> getListAll(PostSearch postSearch) {
                return postRepository.getList(postSearch).stream().map(PostResponse::new).collect(Collectors.toList());
        }

        @Transactional
        public PostResponse edit(Long id, PostEdit postEdit) {
                Post post = postRepository.findById(id).orElseThrow(() -> new CustomPostNotFoundException());

                PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();
                PostEditor postEditor = postEditorBuilder
                        .title(postEdit.getTitle())
                        .contents(postEdit.getContents())
                        .build();

                post.edit(postEditor);

                return new PostResponse(post);
        }

        @Transactional
        public void delete(Long postId) {
                Post post = postRepository.findById(postId).orElseThrow(() -> new CustomPostNotFoundException());
                postRepository.delete(post);

        }
}
