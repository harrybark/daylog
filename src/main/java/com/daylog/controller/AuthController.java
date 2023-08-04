package com.daylog.controller;

import com.daylog.common.CMRespDto;
import com.daylog.request.LoginRequest;
import com.daylog.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.info("login {}", loginRequest);
        String accessToken = authService.signIn(loginRequest);
        return new ResponseEntity<>(new CMRespDto<>(1, "success", accessToken), HttpStatus.OK);
    }
}
