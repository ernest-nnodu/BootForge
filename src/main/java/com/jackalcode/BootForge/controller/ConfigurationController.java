package com.jackalcode.BootForge.controller;

import com.jackalcode.BootForge.service.ConfigurationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationController {

    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }
}
