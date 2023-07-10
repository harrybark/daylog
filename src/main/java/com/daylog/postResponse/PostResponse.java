package com.daylog.postResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {

    private final Long id;
    private final String title;
    private final String contents;

}