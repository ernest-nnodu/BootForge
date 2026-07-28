package com.jackalcode.BootForge.service;

import com.jackalcode.BootForge.domain.model.Configuration;
import com.jackalcode.BootForge.dto.GenerateConfigRequest;
import com.jackalcode.BootForge.formatter.PropertiesFormatter;
import com.jackalcode.BootForge.formatter.YamlFormatter;
import com.jackalcode.BootForge.mapper.ConfigurationMapper;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    private final PropertiesFormatter propertiesFormatter;
    private final YamlFormatter yamlFormatter;
    private final ConfigurationMapper configurationMapper;

    public ConfigurationService(PropertiesFormatter propertiesFormatter,
                                YamlFormatter yamlFormatter,
                                ConfigurationMapper configurationMapper) {
        this.propertiesFormatter = propertiesFormatter;
        this.yamlFormatter = yamlFormatter;
        this.configurationMapper = configurationMapper;
    }

    public String generateConfiguration(GenerateConfigRequest configRequest) {

        Configuration config = configurationMapper.toConfiguration(configRequest);
        String configuration;

       switch (configRequest.outputFormat()) {
            case PROPERTIES -> configuration = propertiesFormatter.format(config);
            case YAML -> configuration = yamlFormatter.format(config);
            default -> throw new IllegalArgumentException("Format not supported");
        }

        return configuration;
    }
}
