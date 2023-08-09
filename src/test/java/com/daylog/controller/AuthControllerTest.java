package com.daylog.controller;

import com.daylog.domain.User;
import com.daylog.handler.aop.ValidationAdvice;
import com.daylog.repository.SessionRepository;
import com.daylog.repository.UserRepository;
import com.daylog.request.LoginRequest;
import com.daylog.request.SignupRequest;
import com.daylog.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Autowired
    private SessionRepository sessionRepository;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인이 정상적으로 수행되는지 확인한다.")
    public void 로그인_성공() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        // given
        userRepository.save(User.builder()
                .name("harry")
                .email("harrypmw1@dev.com")
                .password("1234")
                .build()
        );

        LoginRequest login = LoginRequest.builder()
                .email("harrypmw1@dev.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // when

        // then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("로그인이 정상적으로 수행된 후 세션이 생성되는지 확인한다.")
    @Transactional
    public void 로그인_성공_세션생성() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        // given
        User savedUser = User.builder()
                .name("harry")
                .email("harrypmw1@dev.com")
                .password("1234")
                .build();

        userRepository.save(savedUser);

        LoginRequest login = LoginRequest.builder()
                .email("harrypmw1@dev.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // when

        // then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        User findUser = userRepository.findById(savedUser.getId()).orElseThrow(RuntimeException::new);

        assertEquals(1, findUser.getSessions().size());
    }

    @Test
    @DisplayName("로그인이 정상적으로 수행된 후 세션이 응답되는지 확인한다.")
    @Transactional
    public void 로그인_성공_세션생성_응답() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        // given
        User savedUser = User.builder()
                .name("harry")
                .email("harrypmw1@dev.com")
                .password("1234")
                .build();

        userRepository.save(savedUser);

        LoginRequest login = LoginRequest.builder()
                .email("harrypmw1@dev.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // when

        // then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andDo(print());

        User findUser = userRepository.findById(savedUser.getId()).orElseThrow(RuntimeException::new);

        assertEquals(1, findUser.getSessions().size());
    }

    @Test
    @DisplayName("쿠키로 인증이 정상적으로 수행된 후 세션이 응답되는지 확인한다.")
    @Transactional
    public void 쿠키로_인증_세션생성_응답() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        // given
        User savedUser = User.builder()
                .name("harry")
                .email("harrypmw1@dev.com")
                .password("1234")
                .build();

        userRepository.save(savedUser);

        LoginRequest login = LoginRequest.builder()
                .email("harrypmw1@dev.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // when

        // then
        Cookie cookie = new Cookie("SESSION", "07f5fe16-4c23-4915-a67e-a97dde1cf868");
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .cookie(cookie)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andDo(print());

        User findUser = userRepository.findById(savedUser.getId()).orElseThrow(RuntimeException::new);

        assertEquals(1, findUser.getSessions().size());
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