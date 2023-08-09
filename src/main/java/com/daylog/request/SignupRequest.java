package com.daylog.request;

import com.daylog.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
public class SignupRequest {

    private String name;

    private String email;
    private String password;

    @Builder
    public SignupRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .build();
    }
}
