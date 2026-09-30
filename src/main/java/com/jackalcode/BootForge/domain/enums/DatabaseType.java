package com.jackalcode.BootForge.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Supported database types for datasource configuration")
public enum DatabaseType {
    POSTGRESQL,
    MYSQL
}
