package com.jackalcode.BootForge.dto;

import com.jackalcode.BootForge.domain.model.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record GenerateConfigRequest(

        @Valid
        @NotNull
        ApplicationConfigRequest applicationConfigRequest,

        @Valid
        @NotNull
        ServerConfigRequest serverConfigRequest,

        @Valid
        @NotNull
        DatabaseConfigRequest databaseConfigRequest,

        @Valid
        JpaConfigRequest jpaConfigRequest,

        @Valid
        HikariConfigRequest hikariConfigRequest,

        @Valid
        LoggingConfigRequest loggingConfigRequest,

        @Valid
        ActuatorConfigRequest actuatorConfigRequest,

        OutputFormat outputFormat
) {
}
