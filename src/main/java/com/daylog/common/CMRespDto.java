package com.daylog.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class CMRespDto<T> {
    private long code; // 1 : success , 0 fail
    private String message;
    private T data;


}
