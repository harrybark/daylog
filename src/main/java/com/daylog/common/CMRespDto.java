package com.daylog.common;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CMRespDto<T> {
    private long code; // 1 : success , 0 fail
    private String message;
    private T data;

}
