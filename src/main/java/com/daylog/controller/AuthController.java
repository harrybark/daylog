package com.daylog.controller;

import com.daylog.common.CMRespDto;
import com.daylog.config.AppConfig;
import com.daylog.request.SignupRequest;
import com.daylog.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        authService.signup(signupRequest);
        return ResponseEntity.ok()
                //.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(new CMRespDto<>(1, "success", null))
                ;
    }
}
