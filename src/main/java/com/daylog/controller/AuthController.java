package com.daylog.controller;

import com.daylog.common.CMRespDto;
import com.daylog.config.AppConfig;
import com.daylog.request.LoginRequest;
import com.daylog.request.SignupRequest;
import com.daylog.response.SessionResponse;
import com.daylog.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import javax.validation.Valid;
import java.security.Key;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;

    private final String KEY = "v8up323RprJyfvrYvGCHYZG5jq3vgFg7tkhaJoQhY6U=";


    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("login {}", loginRequest);
        Long userId = authService.signIn(loginRequest);

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        SecretKey secretKey = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(secretKey)
                .setIssuedAt(new Date())
                .compact();

        return ResponseEntity.ok()
                //.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(new CMRespDto<>(1, "success", new SessionResponse(jws)))
                ;

    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        authService.signup(signupRequest);
        return ResponseEntity.ok()
                //.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(new CMRespDto<>(1, "success", null))
                ;
    }
}
