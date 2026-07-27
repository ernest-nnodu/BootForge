package com.jackalcode.BootForge.service;

import com.jackalcode.BootForge.common.ConfigurationTestHelper;
import com.jackalcode.BootForge.common.GenerateConfigRequestTestHelper;
import com.jackalcode.BootForge.common.RequestProps;
import com.jackalcode.BootForge.domain.enums.*;
import com.jackalcode.BootForge.formatter.PropertiesFormatter;
import com.jackalcode.BootForge.formatter.YamlFormatter;
import com.jackalcode.BootForge.mapper.ConfigurationMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConfigurationServiceTest {


    @Mock
    private PropertiesFormatter propertiesFormatter;

    @Mock
    private YamlFormatter yamlFormatter;

    @Mock
    private ConfigurationMapper configurationMapper;

    @InjectMocks
    private ConfigurationService configurationService;

    @Test
    @DisplayName("generateConfiguration generates properties configuration when output format is properties")
    void generateConfiguration_whenOutputFormatIsProperties_shouldReturnProperties() {

        var requestProps = RequestProps.builder()
                .applicationName("test-app")
                .serverPort(8080)
                .databaseName("test-db")
                .username("test-user")
                .password("password")
                .databaseType(DatabaseType.POSTGRESQL)
                .databasePort(5555)
                .outputFormat(OutputFormat.PROPERTIES)
                .build();
        var configRequest = GenerateConfigRequestTestHelper.generateConfigRequest(requestProps);
        var configuration = ConfigurationTestHelper.toConfiguration(configRequest);

        String expectedOutput = """
                spring.application.name=boot-forge
                server.port=8080
                """;

        when(configurationMapper.toConfiguration(configRequest))
                .thenReturn(configuration);

        when(propertiesFormatter.format(configuration))
                .thenReturn(expectedOutput);

        String result = configurationService.generateConfiguration(configRequest);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedOutput);

        verify(configurationMapper).toConfiguration(configRequest);
        verify(propertiesFormatter).format(configuration);
        verifyNoInteractions(yamlFormatter);


    }
}
