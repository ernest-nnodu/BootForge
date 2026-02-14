package com.jackalcode.BootForge.domain.model;

import com.jackalcode.BootForge.domain.enums.LogLevel;

public record LoggingConfig(
        LogLevel rootLevel,
        LogLevel springLevel
) {
    public LoggingConfig(LogLevel rootLevel, LogLevel springLevel) {
        this.rootLevel = (rootLevel == null) ? LogLevel.INFO : rootLevel;
        this.springLevel = (springLevel == null) ? LogLevel.INFO : springLevel;
    }
}
