package com.jackalcode.BootForge.dto;

import com.jackalcode.BootForge.domain.enums.DatabaseType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatabaseConfigRequest(
        @NotNull
        DatabaseType databaseType,

        @NotBlank
        String username,

        @NotBlank
        String password,

        String host,
        String databaseName,

        @Min(1)
        @Max(65535)
        Integer port
) {
}
