package com.daylog.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@ToString
public class PostCreate {

    @NotEmpty
    private String title;
    private String contents;

}
