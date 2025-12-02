package com.ms.oauth.core.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_ARGUMENT("Invalid argument"),
    ;

    private final String message;
}
