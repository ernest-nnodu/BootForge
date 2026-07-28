package com.jackalcode.BootForge.mapper;

import com.jackalcode.BootForge.common.GenerateConfigRequestTestHelper;
import com.jackalcode.BootForge.common.RequestProps;
import com.jackalcode.BootForge.domain.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ConfigurationMapperTest {

    private ConfigurationMapper configurationMapper;

    @BeforeEach
    void setUp() {
        configurationMapper = new ConfigurationMapper();
    }

    @Test
    @DisplayName("toConfiguration should map request to configuration when request is valid")
    void toConfiguration_whenValidRequest_shouldReturnConfiguration() {

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

        var generateConfig = GenerateConfigRequestTestHelper.generateConfigRequest(requestProps);

        var result = configurationMapper.toConfiguration(generateConfig);

        assertThat(result).isNotNull();

        assertThat(result.applicationConfig()).isNotNull();
        assertThat(result.applicationConfig().applicationName()).isEqualTo("test-app");

        assertThat(result.serverConfig()).isNotNull();
        assertThat(result.serverConfig().port()).isEqualTo(8080);
        assertThat(result.serverConfig().contextPath()).isEqualTo("/api");

        assertThat(result.databaseConfig()).isNotNull();
        assertThat(result.databaseConfig().databaseType()).isEqualTo(DatabaseType.POSTGRESQL);
        assertThat(result.databaseConfig().host()).isEqualTo("test-host");
        assertThat(result.databaseConfig().port()).isEqualTo(5555);
        assertThat(result.databaseConfig().databaseName()).isEqualTo("test-db");
        assertThat(result.databaseConfig().username()).isEqualTo("test-user");
        assertThat(result.databaseConfig().password()).isEqualTo("password");

        assertThat(result.databaseConfig().getUrl())
                .isEqualTo("jdbc:postgresql://test-host:5555/test-db");

        assertThat(result.jpaConfig()).isNotNull();
        assertThat(result.jpaConfig().ddlAuto()).isEqualTo(DdlAuto.VALIDATE);
        assertThat(result.jpaConfig().showSql()).isTrue();
        assertThat(result.jpaConfig().openInView()).isTrue();

        assertThat(result.hikariConfig()).isNotNull();
        assertThat(result.hikariConfig().minimumIdle()).isEqualTo(5);
        assertThat(result.hikariConfig().maximumPoolSize()).isEqualTo(20);
        assertThat(result.hikariConfig().connectionTimeout()).isEqualTo(20_000L);

        assertThat(result.loggingConfig()).isNotNull();
        assertThat(result.loggingConfig().rootLevel()).isEqualTo(LogLevel.INFO);
        assertThat(result.loggingConfig().springLevel()).isEqualTo(LogLevel.DEBUG);

        assertThat(result.actuatorConfig()).isNotNull();
        assertThat(result.actuatorConfig().exposedEndpoints()).contains("health,info");
        assertThat(result.actuatorConfig().showHealthDetails()).isEqualTo(HealthShowDetails.ALWAYS);
    }

    @Test
    @DisplayName("toConfiguration should throw IllegalArgumentException when application name is not provided")
    void toConfiguration_whenApplicationNameNotProvided_throwsIllegalArgumentException() {

        var requestProps = RequestProps.builder()
                .applicationName("")
                .activeProfile("dev")
                .serverPort(8080)
                .contextPath("/api")
                .databaseName("test-db")
                .host("test-host")
                .username("test-user")
                .password("password")
                .databaseType(DatabaseType.POSTGRESQL)
                .databasePort(5555)
                .build();
        var request = GenerateConfigRequestTestHelper.generateConfigRequest(requestProps);

        assertThatThrownBy(() -> configurationMapper.toConfiguration(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Application name is required");
    }

    @Test
    @DisplayName("toConfiguration should throw IllegalArgumentException when server port is below minimum port number")
    void toConfiguration_whenServerPortIsBelowMinPort_throwsIllegalArgumentException() {

        var requestProps = RequestProps.builder()
                .applicationName("test-app")
                .activeProfile("dev")
                .serverPort(-34)
                .contextPath("/api")
                .databaseName("test-db")
                .host("test-host")
                .username("test-user")
                .password("password")
                .databaseType(DatabaseType.POSTGRESQL)
                .databasePort(5555)
                .build();
        var request = GenerateConfigRequestTestHelper.generateConfigRequest(requestProps);

        assertThatThrownBy(() -> configurationMapper.toConfiguration(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Server port must be between 1 and 65535");
    }

    @Test
    @DisplayName("toConfiguration should throw IllegalArgumentException when server port is above maximum port number")
    void toConfiguration_whenServerPortIsAboveMaxPort_throwsIllegalArgumentException() {

        var requestProps = RequestProps.builder()
                .applicationName("test-app")
                .activeProfile("dev")
                .serverPort(80_000)
                .contextPath("/api")
                .databaseName("test-db")
                .host("test-host")
                .username("test-user")
                .password("password")
                .databaseType(DatabaseType.POSTGRESQL)
                .databasePort(5555)
                .build();
        var request = GenerateConfigRequestTestHelper.generateConfigRequest(requestProps);

        assertThatThrownBy(() -> configurationMapper.toConfiguration(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Server port must be between 1 and 65535");
    }

    @Test
    @DisplayName("toConfiguration should throw IllegalArgumentException when database type not provided")
    void toConfiguration_whenDatabaseTypeNotProvided_throwsIllegalArgumentException() {

        var requestProps = RequestProps.builder()
                .applicationName("test-app")
                .activeProfile("dev")
                .serverPort(8000)
                .contextPath("/api")
                .databaseName("test-db")
                .host("test-host")
                .username("test-user")
                .password("password")
                .databaseType(null)
                .databasePort(5555)
                .build();
        var request = GenerateConfigRequestTestHelper.generateConfigRequest(requestProps);

        assertThatThrownBy(() -> configurationMapper.toConfiguration(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Database type is required");
    }

    @Test
    @DisplayName("toConfiguration should throw IllegalArgumentException when database username not provided")
    void toConfiguration_whenDatabaseUsernameNotProvided_throwsIllegalArgumentException() {

        var requestProps = RequestProps.builder()
                .applicationName("test-app")
                .activeProfile("dev")
                .serverPort(8000)
                .contextPath("/api")
                .databaseName("test-db")
                .host("test-host")
                .username("")
                .password("password")
                .databaseType(DatabaseType.MYSQL)
                .databasePort(5555)
                .build();
        var request = GenerateConfigRequestTestHelper.generateConfigRequest(requestProps);

        assertThatThrownBy(() -> configurationMapper.toConfiguration(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Username is required");
    }

    @Test
    @DisplayName("toConfiguration should throw IllegalArgumentException when database password not provided")
    void toConfiguration_whenDatabasePasswordNotProvided_throwsIllegalArgumentException() {

        var requestProps = RequestProps.builder()
                .applicationName("test-app")
                .activeProfile("dev")
                .serverPort(8000)
                .contextPath("/api")
                .databaseName("test-db")
                .host("test-host")
                .username("test-user")
                .password("")
                .databaseType(DatabaseType.MYSQL)
                .databasePort(5555)
                .build();
        var request = GenerateConfigRequestTestHelper.generateConfigRequest(requestProps);

        assertThatThrownBy(() -> configurationMapper.toConfiguration(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password is required");
    }

    @Test
    @DisplayName("toConfiguration applies default values when optional fields not provided")
    void toConfiguration_whenOptionalFieldsNotProvide_defaultValuesAreUsed() {

        var requestProps = RequestProps.builder()
                .applicationName("test-app")
                .username("test-user")
                .password("password")
                .databaseType(DatabaseType.POSTGRESQL)
                .build();
        var request = GenerateConfigRequestTestHelper.generateConfigRequest(requestProps);

        var result = configurationMapper.toConfiguration(request);

        assertThat(result).isNotNull();

        assertThat(result.applicationConfig()).isNotNull();
        assertThat(result.applicationConfig().applicationName()).isEqualTo("test-app");

        assertThat(result.serverConfig()).isNotNull();
        assertThat(result.serverConfig().port()).isEqualTo(8080);
        assertThat(result.serverConfig().contextPath()).isEqualTo("/");

        assertThat(result.databaseConfig()).isNotNull();
        assertThat(result.databaseConfig().databaseType()).isEqualTo(DatabaseType.POSTGRESQL);
        assertThat(result.databaseConfig().host()).isEqualTo("localhost");
        assertThat(result.databaseConfig().port()).isEqualTo(5432);
        assertThat(result.databaseConfig().databaseName()).isEqualTo("app_db");
        assertThat(result.databaseConfig().username()).isEqualTo("test-user");
        assertThat(result.databaseConfig().password()).isEqualTo("password");

        assertThat(result.databaseConfig().getUrl())
                .isEqualTo("jdbc:postgresql://localhost:5432/app_db");

        assertThat(result.jpaConfig()).isNotNull();
        assertThat(result.jpaConfig().ddlAuto()).isEqualTo(DdlAuto.NONE);
        assertThat(result.jpaConfig().showSql()).isFalse();
        assertThat(result.jpaConfig().openInView()).isFalse();

        assertThat(result.hikariConfig()).isNotNull();
        assertThat(result.hikariConfig().minimumIdle()).isEqualTo(2);
        assertThat(result.hikariConfig().maximumPoolSize()).isEqualTo(10);
        assertThat(result.hikariConfig().connectionTimeout()).isEqualTo(30_000L);

        assertThat(result.loggingConfig()).isNotNull();
        assertThat(result.loggingConfig().rootLevel()).isEqualTo(LogLevel.INFO);
        assertThat(result.loggingConfig().springLevel()).isEqualTo(LogLevel.INFO);

        assertThat(result.actuatorConfig()).isNotNull();
        assertThat(result.actuatorConfig().exposedEndpoints()).contains("health,info");
        assertThat(result.actuatorConfig().showHealthDetails()).isEqualTo(HealthShowDetails.NEVER);
    }
}
