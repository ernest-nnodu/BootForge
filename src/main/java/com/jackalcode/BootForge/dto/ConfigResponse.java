package com.jackalcode.BootForge.dto;

import com.jackalcode.BootForge.domain.enums.OutputFormat;
import io.swagger.v3.oas.annotations.media.Schema;

public record ConfigResponse(

        @Schema(description = "Format of the generated Spring Boot configuration",
                example = "YAML")
        OutputFormat format,

        @Schema(description = "Generated Spring boot configuration content",
        example = """
                server:
                  port: 8080
                spring:
                  application:
                    name: inventory-service
                  profiles:
                    active: prod
                management:
                  endpoints:
                    web:
                      exposure:
                        include: health,info
                  endpoint:
                    health:
                      show-details: ALWAYS
                """)
        String content
) {
}
