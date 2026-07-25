package com.jackalcode.BootForge.controller;

import com.jackalcode.BootForge.common.GenerateConfigRequestTestHelper;
import com.jackalcode.BootForge.common.PropertiesResponseTestHelper;
import com.jackalcode.BootForge.domain.enums.DatabaseType;
import com.jackalcode.BootForge.domain.enums.OutputFormat;
import com.jackalcode.BootForge.common.RequestProps;
import com.jackalcode.BootForge.dto.GenerateConfigRequest;
import com.jackalcode.BootForge.service.ConfigurationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConfigurationController.class)
public class ConfigurationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ConfigurationService configurationService;

    private static final String ENDPOINT = "/api/v1/configurations/generate";

    @Test
    @DisplayName("generateConfig should return configuration and status 200 when request is valid")
    void generateConfig_whenValidRequest_shouldReturnConfigurationAndStatus200() throws Exception {

        var configRequestProps = RequestProps.builder()
                .applicationName("test-app")
                .activeProfile("dev")
                .serverPort(8080)
                .contextPath("/api")
                .databaseName("test-db")
                .databaseType(DatabaseType.POSTGRESQL)
                .databasePort(5432)
                .username("test-user")
                .password("test-password")
                .outputFormat(OutputFormat.PROPERTIES)
                .build();
        var configRequest = GenerateConfigRequestTestHelper.generateConfigRequest(configRequestProps);
        var expectedResponse = PropertiesResponseTestHelper.expectedProperties(configRequest);

        when(configurationService.generateConfiguration(any(GenerateConfigRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(configRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        verify(configurationService).generateConfiguration(configRequest);
    }

    @Test
    @DisplayName("generateConfig should return status 400 when request is invalid")
    void generateConfig_whenInvalidRequest_shouldReturnStatus400() throws Exception {

        var configRequestProps = RequestProps
                .builder()
                .build();

        var configRequest = GenerateConfigRequestTestHelper.generateConfigRequest(configRequestProps);

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(configRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("generateConfig should return status 400 when server port is below minimum port number")
    void generateConfig_whenServerPortIsBelowMin_shouldReturnStatus400() throws Exception {

        var configRequestProps = RequestProps
                .builder()
                .applicationName("test-app")
                .activeProfile("dev")
                .serverPort(-10)
                .contextPath("/api")
                .databaseName("test-db")
                .databaseType(DatabaseType.POSTGRESQL)
                .databasePort(5432)
                .username("test-user")
                .password("test-password")
                .outputFormat(OutputFormat.PROPERTIES)
                .build();

        var configRequest = GenerateConfigRequestTestHelper.generateConfigRequest(configRequestProps);

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(configRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Port number must be greater than 0")));
    }

    @Test
    @DisplayName("generateConfig should return status 400 when server port is above maximum port number")
    void generateConfig_whenServerPortIsAboveMax_shouldReturnStatus400() throws Exception {

        var configRequestProps = RequestProps
                .builder()
                .applicationName("test-app")
                .activeProfile("dev")
                .serverPort(70_000)
                .contextPath("/api")
                .databaseName("test-db")
                .databaseType(DatabaseType.POSTGRESQL)
                .databasePort(5432)
                .username("test-user")
                .password("test-password")
                .outputFormat(OutputFormat.PROPERTIES)
                .build();

        var configRequest = GenerateConfigRequestTestHelper.generateConfigRequest(configRequestProps);

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(configRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Port number must be less than or equal to 65535")));
    }

    @Test
    void generateConfig_whenOutputFormatNotProvided_shouldReturnStatus400() throws Exception {

        var configRequestProps = RequestProps
                .builder()
                .applicationName("test-app")
                .activeProfile("dev")
                .serverPort(1024)
                .contextPath("/api")
                .databaseName("test-db")
                .databaseType(DatabaseType.POSTGRESQL)
                .databasePort(5435)
                .username("test-user")
                .password("test-password")
                .build();

        var configRequest = GenerateConfigRequestTestHelper.generateConfigRequest(configRequestProps);

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(configRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Output format is required")));
    }
}
