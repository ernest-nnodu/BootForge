package com.jackalcode.BootForge.formatter;

import org.junit.jupiter.api.BeforeEach;
import org.yaml.snakeyaml.Yaml;

public class YamlFormatterTest {

    private YamlFormatter yamlFormatter;
    private Yaml yaml;

    @BeforeEach
    void setUp() {
        yamlFormatter = new YamlFormatter();
        yaml = new Yaml();
    }
}
