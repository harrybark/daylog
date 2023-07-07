package com.daylog.controller;

import com.daylog.handler.ControllerExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/posts 요청시 Hello World를 출력한다.")
    public void post_test() throws Exception {
        // given
        String json = "{\"title\":\"글 제목입니다.\", \"contents\":\"글 내용입니다.\"}";

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World"))
                .andDo(print())
        ;
    }

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new PostController())
                .setControllerAdvice(ControllerExceptionHandler.class)
                .build();
    }
    @Test
    @DisplayName("/posts 요청시 Title 값은 필수 인지 확인한다.")
    public void post_test_valid() throws Exception {
        // given
        String json = "{\"title\": \"\", \"contents\":\"\"}";

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andDo(print())
        ;
    }
}