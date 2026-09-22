package com.jackalcode.BootForge.controller;

import com.jackalcode.BootForge.dto.ConfigResponse;
import com.jackalcode.BootForge.dto.GenerateConfigRequest;
import com.jackalcode.BootForge.service.ConfigurationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/configurations")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping(path = "/generate")
    public ResponseEntity<ConfigResponse> generateConfig(
            @RequestBody @Valid GenerateConfigRequest configRequest) {

        var response = configurationService.generateConfiguration(configRequest);
        return ResponseEntity.ok(response);
    }
}
