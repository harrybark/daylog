package com.daylog.request;

import com.daylog.handler.ex.CustomInvalidRequestException;
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

    public void validate() {
        //조건
        if ( title.contains("바보")) {
            throw new CustomInvalidRequestException("title", "바보가 포함되면 안됩니다.");
        }
    }

}
