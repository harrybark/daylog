package com.daylog.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
@NoArgsConstructor
public class PostCreate {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank
    private String contents;

    @Builder
    public PostCreate(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
