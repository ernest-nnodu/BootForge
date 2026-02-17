package com.jackalcode.BootForge.service;

import com.jackalcode.BootForge.formatter.PropertiesFormatter;
import com.jackalcode.BootForge.formatter.YamlFormatter;
import com.jackalcode.BootForge.mapper.ConfigurationMapper;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    private final PropertiesFormatter propertiesFormatter;
    private final YamlFormatter yamlFormatter;
    private final ConfigurationMapper configurationMapper;

    public ConfigurationService(PropertiesFormatter propertiesFormatter, YamlFormatter yamlFormatter, ConfigurationMapper configurationMapper) {
        this.propertiesFormatter = propertiesFormatter;
        this.yamlFormatter = yamlFormatter;
        this.configurationMapper = configurationMapper;
    }
}
