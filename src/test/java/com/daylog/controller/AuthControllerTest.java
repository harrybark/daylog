package com.daylog.controller;

import com.daylog.handler.aop.ValidationAdvice;
import com.daylog.repository.UserRepository;
import com.daylog.request.SignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import({ValidationAdvice.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AuthController controller;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        userRepository.deleteAll();
    }

    @Test
    @DisplayName(value = "회원가입이 성공적으로 수행된다.")
    public void 회원가입_정상수행_테스트() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // given
        SignupRequest signupRequest = SignupRequest.builder()
                .name("Harry Park")
                .password("1234")
                .email("harrypmw@dev.com")
                .build();

        String json = objectMapper.writeValueAsString(signupRequest);

        // when

        // then
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}