package com.jackalcode.BootForge.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Supported Hibernate database schema management strategies")
public enum DdlAuto {
    NONE,
    VALIDATE,
    UPDATE,
    CREATE,
    CREATE_DROP
}
