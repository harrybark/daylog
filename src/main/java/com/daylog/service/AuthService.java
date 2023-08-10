package com.daylog.service;

import com.daylog.crypto.PasswordEncoder;
import com.daylog.crypto.ScryptPasswordEncoder;
import com.daylog.domain.User;
import com.daylog.handler.ex.CustomAlreadyExistsEmailException;
import com.daylog.handler.ex.CustomInvalidRequestException;
import com.daylog.repository.UserRepository;
import com.daylog.request.LoginRequest;
import com.daylog.request.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false)
    public Long signIn(LoginRequest loginRequest) {
        /*
        User user = userRepository.findUserByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
                .orElseThrow(CustomInvalidRequestException::new);
        */

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(CustomInvalidRequestException::new);

        if ( !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()) ) {
            throw new CustomInvalidRequestException();
        }

        //Session session = user.addSession();
        return user.getId();
    }

    @Transactional(readOnly = false)
    public Long signup(SignupRequest signupRequest) {

        boolean isPresent = userRepository.findByEmail(signupRequest.getEmail()).isPresent();

        if ( isPresent ) {
            throw new CustomAlreadyExistsEmailException();
        }

        String encryptPassword = passwordEncoder.encrypt(signupRequest.getPassword());
        signupRequest.setPassword(encryptPassword);

        User savedUser = userRepository.save(signupRequest.toEntity());

        return savedUser.getId();
    }
}
