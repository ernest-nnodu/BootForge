package com.jackalcode.BootForge.exception;

import java.time.Instant;

public record ApiErrorResponse(
        ErrorCode errorCode,
        String message,
        Instant timestamp
) {

    public ApiErrorResponse(ErrorCode errorCode, String message) {
        this(errorCode, message, Instant.now());
    }
}
