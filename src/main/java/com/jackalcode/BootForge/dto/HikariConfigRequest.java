package com.jackalcode.BootForge.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record HikariConfigRequest(
        @Schema(
                description = "Maximum number of connections allowed in the connection pool",
                example = "10"
        )
        Integer maximumPoolSize,

        @Schema(
                description = "Minimum number of idle connections maintained in the connection pool",
                example = "5"
        )
        Integer minimumIdle,

        @Schema(
                description = "Maximum time in milliseconds to wait for a database connection from the pool",
                example = "30000"
        )
        Long connectionTimeout
) {
}
