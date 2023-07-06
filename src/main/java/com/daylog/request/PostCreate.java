package com.daylog.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@ToString
public class PostCreate {

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

}
