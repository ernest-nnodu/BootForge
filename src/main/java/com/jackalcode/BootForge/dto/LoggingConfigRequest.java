package com.jackalcode.BootForge.dto;

import com.jackalcode.BootForge.domain.enums.LogLevel;

public record LoggingConfigRequest(
        LogLevel rootLevel,
        LogLevel springLevel
) {
}
