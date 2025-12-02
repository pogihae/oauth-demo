package com.ms.oauth.core.common.exception;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class ServerException extends RuntimeException {

    private final ErrorCode errorCode;

    private final String message;

    public ServerException(ErrorCode errorCode, String message) {
        Assert.notNull(errorCode, "errorCode must not be null");
        this.errorCode = errorCode;
        this.message = message;
    }

    public ServerException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage());
    }
}
