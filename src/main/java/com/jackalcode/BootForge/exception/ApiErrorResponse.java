package com.jackalcode.BootForge.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record ApiErrorResponse(

        @Schema(
                description = "Machine-readable code identifying the type of error",
                example = "VALIDATION_ERROR"
        )
        ErrorCode errorCode,

        @Schema(
                description = "Human-readable description of the error",
                example = "Request validation failed"
        )
        String message,

        @Schema(
                description = "Timestamp indicating when the error occurred",
                example = "2026-09-24T20:30:00Z"
        )
        Instant timestamp
) {

    public ApiErrorResponse(ErrorCode errorCode, String message) {
        this(errorCode, message, Instant.now());
    }
}
