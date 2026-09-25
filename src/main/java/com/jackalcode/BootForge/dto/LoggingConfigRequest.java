package com.jackalcode.BootForge.dto;

import com.jackalcode.BootForge.domain.enums.LogLevel;
import io.swagger.v3.oas.annotations.media.Schema;

public record LoggingConfigRequest(

        @Schema(
                description = "Root logging level applied as the default across the application",
                example = "INFO"
        )
        LogLevel rootLevel,

        @Schema(
                description = "Logging level applied to Spring Framework components",
                example = "INFO"
        )
        LogLevel springLevel
) {
}
