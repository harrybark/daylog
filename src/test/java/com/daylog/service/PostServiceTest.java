package com.daylog.service;

import com.daylog.domain.Post;
import com.daylog.handler.ex.CustomValidationApiException;
import com.daylog.response.PostResponse;
import com.daylog.repository.PostRepository;
import com.daylog.request.PostCreate;
import com.daylog.request.PostEdit;
import com.daylog.request.PostSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.by;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void init()
    {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName(value = "글 작성")
    public void 글이_정상적으로_저장된다() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("Harry Potter")
                .contents("prologue")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertEquals(1L, postRepository.count());
    }

    @Test
    @DisplayName(value = "게시글 1개를 조회한다.")
    public void 게시글_단건_조회() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("Harry Potter 2")
                .contents("prologue")
                .build();

        // when
        Long postId = postService.write(postCreate);
        PostResponse postResponse = postService.get(postId);

        // then
        assertEquals(postId, postResponse.getId());
    }

    @Test
    @DisplayName(value = "게시글 모두를 조회한다.")
    public void 게시글_다건_조회() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i ->
                    Post.builder()
                            .title("Harry Potter " + i)
                            .contents("Contents + " + i)
                            .build()
                ).collect(Collectors.toList());


        // when
        postRepository.saveAll(requestPosts);

        PageRequest pageRequest = of(0, 10, by(DESC, "id"));
        Page<PostResponse> postResponse = postService.getList(pageRequest);

        // then
        assertEquals(10, postResponse.getSize());
    }

    @Test
    @DisplayName(value = "게시글 모두를 조회한다.(QueryDSL)")
    public void 게시글_다건_조회_QUERYDSL() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i ->
                        Post.builder()
                                .title("Harry Potter " + i)
                                .contents("Contents + " + i)
                                .build()
                ).collect(Collectors.toList());


        // when
        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch
                .builder()
                .build();

        List<PostResponse> postResponse = postService.getListAll(postSearch);

        // then
        assertEquals(10, postResponse.size());
        assertEquals("Harry Potter 19", postResponse.get(0).getTitle());
    }

    @Test
    @DisplayName(value = "게시글 1개를 수정한다.")
    @Transactional
    public void 게시글_단건_수정() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("Harry Potter")
                .contents("prologue")
                .build();

        // when
        Long postId = postService.write(postCreate);
        PostEdit postEdit = PostEdit.builder()
                .title("Harry Potter2")
                .contents("J.K Rolling")
                .build();

        postService.edit(postId, postEdit);

        // then
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomValidationApiException("Not Found post"));
        assertEquals("Harry Potter2", post.getTitle());

    }

    @Test
    @DisplayName(value = "게시글 삭제")
    public void 게시글_삭제한다() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("Harry Potter")
                .contents("prologue")
                .build();

        // when
        Long postId = postService.write(postCreate);

        // when
        postService.delete(postId);
        // then
        assertEquals(0, postRepository.count());

    }
}