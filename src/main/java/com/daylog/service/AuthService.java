package com.daylog.service;

import com.daylog.domain.User;
import com.daylog.handler.ex.CustomInvalidRequestException;
import com.daylog.repository.UserRepository;
import com.daylog.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void signIn(LoginRequest loginRequest) {
        User user = userRepository.findUserByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
                .orElseThrow(CustomInvalidRequestException::new);

        user.addSession();

    }
}
