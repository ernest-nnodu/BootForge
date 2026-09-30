package com.jackalcode.BootForge.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Supported logging levels for the generated Spring Boot configuration")
public enum LogLevel {
    TRACE,
    DEBUG,
    INFO,
    WARN,
    ERROR
}
