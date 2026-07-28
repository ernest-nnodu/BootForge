package com.jackalcode.BootForge.formatter;

import com.jackalcode.BootForge.common.ConfigurationTestHelper;
import com.jackalcode.BootForge.common.GenerateConfigRequestTestHelper;
import com.jackalcode.BootForge.common.RequestProps;
import com.jackalcode.BootForge.domain.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesFormatterTest {

    private PropertiesFormatter propertiesFormatter;

    @BeforeEach
    void setUp() {
        propertiesFormatter = new PropertiesFormatter();
    }

    @Test
    @DisplayName("format should return formatted properties")
    void format_whenCalled_shouldReturnFormattedProperties() {

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
        var configuration = ConfigurationTestHelper.toConfiguration(configRequest);

        var result = propertiesFormatter.format(configuration);

        assertThat(result)
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
