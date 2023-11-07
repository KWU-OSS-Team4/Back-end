package com.witheat.WithEatServer.common;

import com.witheat.WithEatServer.Exception.BaseException;

import lombok.Getter;

@Getter
public class BaseErrorResponse {
    private final int status;
    private final String message;

    public BaseErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseErrorResponse(BaseException baseException) {
        this.status = baseException.getCode();
        this.message = baseException.getMessage();
    }
}
