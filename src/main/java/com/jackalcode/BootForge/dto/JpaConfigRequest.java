package com.jackalcode.BootForge.dto;

import com.jackalcode.BootForge.domain.enums.DdlAuto;
import io.swagger.v3.oas.annotations.media.Schema;

public record JpaConfigRequest(

        @Schema(
                description = "Strategy used by Hibernate to manage the database schema",
                example = "UPDATE"
        )
        DdlAuto ddlAuto,

        @Schema(
                description = "Whether generated SQL statements are logged",
                example = "false"
        )
        Boolean showSql,

        @Schema(
                description = "Whether the JPA persistence context remains open during web request processing",
                example = "false"
        )
        Boolean openInView
) {
}
