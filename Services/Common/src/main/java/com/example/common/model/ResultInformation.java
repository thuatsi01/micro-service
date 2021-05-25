package com.example.common.model;

import com.example.common.exception.ErrorCode;
import lombok.Value;

@Value
public class ResultInformation {
    Integer code;
    String message;

    private static ResultInformation of(Integer code, String message) {
        return new ResultInformation(code, message);
    }

    public static ResultInformation ok() {
        return of(ErrorCode.SUCCESS, "");
    }
}
