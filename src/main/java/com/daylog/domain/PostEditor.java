package com.daylog.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostEditor {

    private String title;
    private String contents;

    @Builder
    public PostEditor(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
