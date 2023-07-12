package com.daylog.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PostEdit {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank
    private String contents;

    @Builder
    public PostEdit(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
