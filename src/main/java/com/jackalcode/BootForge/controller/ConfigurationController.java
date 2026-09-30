package com.jackalcode.BootForge.controller;

import com.jackalcode.BootForge.dto.ConfigResponse;
import com.jackalcode.BootForge.dto.GenerateConfigRequest;
import com.jackalcode.BootForge.exception.ApiErrorResponse;
import com.jackalcode.BootForge.service.ConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/configurations")
@Tag(
        name = "Configuration",
        description = "Generate Spring Boot application configuration"
)
public class ConfigurationController {

    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Operation(
            summary = "Generate Spring Boot configuration",
            description = """
                    Generates Spring Boot application configuration from the
                    supplied settings. Configuration can be generated in
                    YAML or properties format.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Configuration generated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ConfigResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid configuration request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ApiErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ApiErrorResponse.class
                            )
                    )
            )
    })
    @PostMapping(path = "/generate")
    public ResponseEntity<ConfigResponse> generateConfig(
            @RequestBody @Valid GenerateConfigRequest configRequest) {

        var response = configurationService.generateConfiguration(configRequest);
        return ResponseEntity.ok(response);
    }
}
