package com.jackalcode.BootForge.controller;

import com.jackalcode.BootForge.dto.GenerateConfigRequest;
import com.jackalcode.BootForge.service.ConfigurationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> generateConfig(
            @RequestBody @Valid GenerateConfigRequest configRequest) {

        String configResponse = configurationService.generateConfiguration(configRequest);
        return new ResponseEntity<>(configResponse, HttpStatus.OK);
    }
}
