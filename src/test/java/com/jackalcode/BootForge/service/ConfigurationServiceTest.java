package com.jackalcode.BootForge.service;

import com.jackalcode.BootForge.formatter.PropertiesFormatter;
import com.jackalcode.BootForge.formatter.YamlFormatter;
import org.junit.jupiter.api.BeforeEach;

public class ConfigurationServiceTest {

    private PropertiesFormatter propertiesFormatter;
    private YamlFormatter yamlFormatter;

    @BeforeEach
    void setUp() {
        propertiesFormatter = new PropertiesFormatter();
        yamlFormatter = new YamlFormatter();
    }
}
