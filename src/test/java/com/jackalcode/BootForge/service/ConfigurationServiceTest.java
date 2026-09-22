package com.jackalcode.BootForge.service;

import com.jackalcode.BootForge.common.ConfigurationTestHelper;
import com.jackalcode.BootForge.common.GenerateConfigRequestTestHelper;
import com.jackalcode.BootForge.common.RequestProps;
import com.jackalcode.BootForge.domain.enums.*;
import com.jackalcode.BootForge.dto.ConfigResponse;
import com.jackalcode.BootForge.formatter.PropertiesFormatter;
import com.jackalcode.BootForge.formatter.YamlFormatter;
import com.jackalcode.BootForge.mapper.ConfigurationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

    private ConfigurationService configurationService;

    @BeforeEach
    void setUp() {

        configurationService = new ConfigurationService(
                List.of(propertiesFormatter, yamlFormatter),
                configurationMapper);
    }

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

        String expectedContent = """
                spring.application.name=boot-forge
                server.port=8080
                spring.datasource.username=test-user
                spring.datasource.password=password
                spring.datasource.url=jdbc:postgresql://test-host:5555/test-db
                """;

        var expectedProperties = new ConfigResponse(OutputFormat.PROPERTIES, expectedContent);

        when(propertiesFormatter.getFormat()).thenReturn(OutputFormat.PROPERTIES);

        when(configurationMapper.toConfiguration(configRequest))
                .thenReturn(configuration);

        when(propertiesFormatter.format(configuration))
                .thenReturn(expectedContent);

        var result = configurationService.generateConfiguration(configRequest);

        assertThat(result).isNotNull();
        assertThat(result.format()).isEqualTo(expectedProperties.format());
        assertThat(result.content()).isEqualTo(expectedProperties.content());

        verify(configurationMapper).toConfiguration(configRequest);
        verify(propertiesFormatter).format(configuration);
        verifyNoMoreInteractions(yamlFormatter);
    }

    @Test
    @DisplayName("generateConfiguration generates yaml configuration when output format is yaml")
    void generateConfiguration_whenOutputFormatIsYaml_shouldReturnYaml() {

        var requestProps = RequestProps.builder()
                .applicationName("test-app")
                .serverPort(8080)
                .databaseName("test-db")
                .username("test-user")
                .password("password")
                .databaseType(DatabaseType.POSTGRESQL)
                .databasePort(5555)
                .outputFormat(OutputFormat.YAML)
                .build();
        var configRequest = GenerateConfigRequestTestHelper.generateConfigRequest(requestProps);
        var configuration = ConfigurationTestHelper.toConfiguration(configRequest);

        String expectedContent = """
        spring:
          application:
            name: bootforge

          datasource:
            url: jdbc:postgresql://localhost:5432/bootforge_db
            username: postgres
            password: password

          jpa:
            database-platform: org.hibernate.dialect.PostgreSQLDialect
            show-sql: true
            open-in-view: false

            hibernate:
              ddl-auto: validate

        server:
          port: 8080
        """;

        var expectedYaml = new ConfigResponse(OutputFormat.YAML, expectedContent);

        when(propertiesFormatter.getFormat()).thenReturn(OutputFormat.PROPERTIES);
        when(yamlFormatter.getFormat()).thenReturn(OutputFormat.YAML);
        when(configurationMapper.toConfiguration(configRequest))
                .thenReturn(configuration);
        when(yamlFormatter.format(configuration))
                .thenReturn(expectedContent);

        var result = configurationService.generateConfiguration(configRequest);

        assertThat(result).isNotNull();
        assertThat(result.format()).isEqualTo(expectedYaml.format());
        assertThat(result.content()).isEqualTo(expectedYaml.content());

        verify(configurationMapper).toConfiguration(configRequest);
        verify(yamlFormatter).format(configuration);
        verifyNoMoreInteractions(propertiesFormatter);
    }
}
