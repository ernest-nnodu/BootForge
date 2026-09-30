package com.jackalcode.BootForge.service;

import com.jackalcode.BootForge.domain.model.Configuration;
import com.jackalcode.BootForge.dto.ConfigResponse;
import com.jackalcode.BootForge.dto.GenerateConfigRequest;
import com.jackalcode.BootForge.exception.UnsupportedFormatException;
import com.jackalcode.BootForge.formatter.ConfigFormatter;
import com.jackalcode.BootForge.mapper.ConfigurationMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationService {

    private final List<ConfigFormatter> formatters;
    private final ConfigurationMapper configurationMapper;

    public ConfigurationService(List<ConfigFormatter> formatters, ConfigurationMapper configurationMapper) {
        this.formatters = formatters;
        this.configurationMapper = configurationMapper;
    }

    public ConfigResponse generateConfiguration(GenerateConfigRequest configRequest) {

        Configuration config = configurationMapper.toConfiguration(configRequest);

        //Select a formatter based on the output format
        ConfigFormatter formatter = formatters.stream()
                .filter(f -> f.getFormat().equals(configRequest.outputFormat()))
                .findFirst()
                .orElseThrow(() -> new UnsupportedFormatException(configRequest.outputFormat()));

        String content = formatter.format(config);

        return new ConfigResponse(
                configRequest.outputFormat(),
                content
        );
    }
}
