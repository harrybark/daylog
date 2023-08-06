package com.daylog.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Session {

    @Id
    @GeneratedValue
    private Long id;

    private String accessToken;

    private LocalDateTime expiredDt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Session(User user) {
        this.accessToken = UUID.randomUUID().toString();
        this.expiredDt = LocalDateTime.now().plusMinutes(10L);
        this.user = user;
    }
}