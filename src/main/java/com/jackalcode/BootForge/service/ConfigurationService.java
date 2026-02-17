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

        String configuration;

       switch (configRequest.outputFormat()) {
            case PROPERTIES -> {
                Configuration config = configurationMapper.toDomain(configRequest);
                configuration = propertiesFormatter.format(config);
            }
            case YAML -> {
                Configuration config = configurationMapper.toDomain(configRequest);
                configuration = yamlFormatter.format(config);
            }
            default -> throw new IllegalArgumentException("Format not supported");
        }

        return configuration;
    }
}
