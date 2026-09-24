package com.jackalcode.BootForge.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Supported output formats for the generated Spring Boot configuration")
public enum OutputFormat {
    PROPERTIES,
    YAML
}
