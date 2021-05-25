package com.example.common.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends Exception {
    Integer code;
}
