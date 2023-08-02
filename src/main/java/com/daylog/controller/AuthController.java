package com.daylog.controller;

import com.daylog.domain.User;
import com.daylog.handler.ex.CustomInvalidRequestException;
import com.daylog.repository.UserRepository;
import com.daylog.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/auth/login")
    public void login(@RequestBody LoginRequest loginRequest) {
        log.info("login {}", loginRequest);

        User user = userRepository.findUserByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
                .orElseThrow(CustomInvalidRequestException::new);


    }
}
