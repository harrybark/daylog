package com.daylog.controller;

import com.daylog.domain.Post;
import com.daylog.handler.ControllerExceptionHandler;
import com.daylog.handler.aop.ValidationAdvice;
import com.daylog.repository.PostRepository;
import com.daylog.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import({ValidationAdvice.class})
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PostController controller;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        postRepository.deleteAll();
    }
    @Test
    @DisplayName("/posts 요청시 Title 값은 필수 인지 확인한다.")
    public void post_test_valid() throws Exception {
        // given
        String json = "{\"title\": null, \"contents\":\"\"}";

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.title").isNotEmpty())
                .andDo(print())
        ;

        // then
    }

    @Test
    @DisplayName("/posts 요청시 저장이되는지 확인한다.")
    public void post_test_success() throws Exception {
        // given
        String title = "Harry Potter2";
        String contents = "Prologue";
        PostCreate postCreate = PostCreate.builder()
                .title(title)
                .contents(contents)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(postCreate);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andExpect(status().isCreated())
                .andDo(print());

        // then
        assertEquals(1L, postRepository.count());
    }

    @Test
    @DisplayName("/posts 요청시 저장이되는지 확인한다.")
    public void post_test_success_2nd() throws Exception {
        // given
        String title = "Harry Potter2";
        String contents = "Prologue";
        PostCreate postCreate = PostCreate.builder()
                .title(title)
                .contents(contents)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(postCreate);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andExpect(status().isCreated())
                .andDo(print());

        // then
        assertEquals(1L, postRepository.count());
    }

    @Test
    @DisplayName(value = "글 단건을 조회한다")
    public void 글_단건_조회() throws Exception {
        // given
        String title = "Harry Potter2";
        String contents = "Prologue";
        Post postCreate = Post.builder()
                .title(title)
                .contents(contents)
                .build();

        postRepository.save(postCreate);

        mockMvc.perform(get("/posts/{postId}", postCreate.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(postCreate.getId()))
                .andExpect(jsonPath("$.data.title").value(title))
                .andExpect(jsonPath("$.data.contents").value(contents))
                .andDo(print());

        // then

    }

    @Test
    @DisplayName(value = "게시글 모두를 조회한다.")
    public void 게시글_다건_조회() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i ->
                        Post.builder()
                                .title("Harry Potter " + i)
                                .contents("Contents " + i)
                                .build()
                ).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        mockMvc.perform(get("/posts-all?page=1&size=10&sort=id,desc")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // then

    }


    @Test
    @DisplayName(value = "게시글 모두를 조회한다.(QueryDSL)")
    public void 게시글_다건_조회_QUERYDSL() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i ->
                        Post.builder()
                                .title("Harry Potter " + i)
                                .contents("Contents " + i)
                                .build()
                ).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // then

    }

    @Test
    @DisplayName(value = "페이지를 0으로 불렀을 때, 조회가 성공하는지 확인한다.")
    public void 게시글_다건_조회_페이지를_0으로조회한다() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i ->
                        Post.builder()
                                .title("Harry Potter " + i)
                                .contents("Contents " + i)
                                .build()
                ).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // then

    }
}