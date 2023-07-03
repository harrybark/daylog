package com.daylog.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PostCreate {

    public String title;
    public String contents;

}
