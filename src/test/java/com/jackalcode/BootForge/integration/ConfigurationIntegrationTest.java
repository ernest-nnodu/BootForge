package com.jackalcode.BootForge.integration;

import com.jackalcode.BootForge.common.GenerateConfigRequestTestHelper;
import com.jackalcode.BootForge.common.RequestProps;
import com.jackalcode.BootForge.domain.enums.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ConfigurationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String ENDPOINT = "/api/v1/configurations/generate";

    @Test
    @DisplayName("generateConfig should return properties configuration when properties request is valid")
    void generateConfig_whenValidPropertiesFormatRequest_shouldReturnPropertiesConfiguration() throws Exception {

        var requestProps = RequestProps.builder()
                .applicationName("test-app")
                .activeProfile("dev")
                .serverPort(8080)
                .contextPath("/api")
                .databaseName("test-db")
                .host("test-host")
                .username("test-user")
                .password("password")
                .databaseType(DatabaseType.POSTGRESQL)
                .databasePort(5555)
                .ddlAuto(DdlAuto.VALIDATE)
                .showSql(true)
                .openInView(true)
                .maximumPoolSize(20)
                .minimumIdle(5)
                .connectionTimeout(20_000L)
                .rootLevel(LogLevel.INFO)
                .springLevel(LogLevel.DEBUG)
                .exposedEndpoints("health,info")
                .showHealthDetails(HealthShowDetails.ALWAYS)
                .outputFormat(OutputFormat.PROPERTIES)
                .build();
        var configRequest = GenerateConfigRequestTestHelper.generateConfigRequest(requestProps);

        var result = mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(configRequest)))
                .andExpect(status().isOk())
                .andReturn();

        var response = result.getResponse().getContentAsString();

        System.out.println(response);

        assertThat(response).isNotNull()
                .contains("spring.application.name=test-app")
                .contains("spring.profiles.active=dev")
                .contains("server.port=8080")
                .contains("server.servlet.context-path=/api")
                .contains(
                        "spring.datasource.url=" +
                                "jdbc:postgresql://test-host:5555/test-db"
                )
                .contains("spring.datasource.username=test-user")
                .contains("spring.datasource.password=password")
                .contains("spring.jpa.hibernate.ddl-auto=validate")
                .contains("spring.jpa.database-platform=" +
                        "org.hibernate.dialect.PostgreSQLDialect")
                .contains("spring.jpa.show-sql=true")
                .contains("spring.jpa.open-in-view=true")
                .contains("spring.datasource.hikari.maximum-pool-size=20")
                .contains("spring.datasource.hikari.minimum-idle=5")
                .contains("spring.datasource.hikari.connection-timeout=20000")
                .contains("logging.level.root=info")
                .contains("logging.level.org.springframework=debug")
                .contains("management.endpoints.web.exposure.include=" +
                        "health,info")
                .contains("management.endpoint.health.show-details=always");
    }
}
