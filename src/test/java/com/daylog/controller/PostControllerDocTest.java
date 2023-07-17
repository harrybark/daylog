package com.daylog.controller;

import com.daylog.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.daylog.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @DisplayName(value = "글 단건 조회 테스트")
    public void 글_단건_조회() throws Exception {
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
        );

        this.mockMvc.perform(get("/posts/{postId}", 1L).accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-inquiry",
                            pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                                    ),
                            responseFields(
                                    fieldWithPath("code").description("Result Code"),
                                    fieldWithPath("message").description("Result Message"),
                                    fieldWithPath("data.id").description("게시글 ID"),
                                    fieldWithPath("data.title").description("게시글 제목"),
                                    fieldWithPath("data.contents").description("게시글 내용")
                            )
                        )
                )
                ;

        // when

        // then

    }

    @Test
    @DisplayName(value = "글 등록 테스트")
    public void 글_등록() throws Exception {
        // given
        String title = "Apple";
        String contents = "Prologue";
        PostCreate postCreate = PostCreate.builder()
                .title(title)
                .contents(contents)
                .build();

        String json = objectMapper.writeValueAsString(postCreate);

        // when
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("post-create",
                                requestFields(
                                        fieldWithPath("title").description("게시글 제목"),
                                        fieldWithPath("contents").description("게시글 내용").optional()
                                ),
                                responseFields(
                                        fieldWithPath("code").description("Result Code"),
                                        fieldWithPath("message").description("Result Message")
                                )
                        )
                )
        ;


        // when

        // then

    }
}
