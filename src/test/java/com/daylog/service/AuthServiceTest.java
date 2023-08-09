package com.daylog.service;

import com.daylog.domain.User;
import com.daylog.handler.ex.CustomAlreadyExistsEmailException;
import com.daylog.repository.UserRepository;
import com.daylog.request.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @BeforeEach
    public void init()
    {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName(value = "회원가입이 성공적으로 수행된다.")
    public void 회원가입_정상수행_테스트() throws Exception {

        // given
        SignupRequest signupRequest = SignupRequest.builder()
                .name("Harry Park")
                .password("1234")
                .email("harrypmw@dev.com")
                .build();

        // when
        authService.signup(signupRequest);

        // then
        assertEquals(1, userRepository.count());
    }

    @Test
    @DisplayName(value = "회원가입시 이메일이 존재하는 경우 오류를 뱉어낸다.")
    public void 회원가입_이메일_중복_오류테스트() throws Exception {

        User user = User.builder()
                .name("devtomato")
                .password("1234")
                .email("harrypmw@dev.com")
                .build();
        userRepository.save(user);

        // given
        SignupRequest signupRequest = SignupRequest.builder()
                .name("Harry Park")
                .password("1234")
                .email("harrypmw@dev.com")
                .build();

        // when


        // then
        assertThrows(CustomAlreadyExistsEmailException.class, () -> authService.signup(signupRequest));

    }
}