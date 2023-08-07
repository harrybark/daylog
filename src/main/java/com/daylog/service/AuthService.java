package com.daylog.service;

import com.daylog.domain.Session;
import com.daylog.domain.User;
import com.daylog.handler.ex.CustomInvalidRequestException;
import com.daylog.repository.UserRepository;
import com.daylog.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public Long signIn(LoginRequest loginRequest) {
        User user = userRepository.findUserByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
                .orElseThrow(CustomInvalidRequestException::new);

        Session session = user.addSession();
        return user.getId();
    }
}
