package com.daylog.domain;

import lombok.*;

import jakarta.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Post {

    @Id @GeneratedValue
    private Long id;

    private String title;

    @Lob
    private String contents;

    @Builder
    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .contents(contents)
                ;
    }

    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        contents = postEditor.getContents();
    }

}
