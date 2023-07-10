package com.daylog.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String contents;

    @Builder
    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
