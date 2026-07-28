package com.jackalcode.BootForge.integration;

import com.jackalcode.BootForge.common.GenerateConfigRequestTestHelper;
import com.jackalcode.BootForge.common.RequestProps;
import com.jackalcode.BootForge.common.YamlResponseTestHelper;
import com.jackalcode.BootForge.domain.enums.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.yaml.snakeyaml.Yaml;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

import static com.jackalcode.BootForge.common.YamlResponseTestHelper.mapAt;
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

    private final Yaml yaml = new Yaml();

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

    @Test
    @DisplayName("generateConfig should return yaml configuration when yaml request is valid")
    void generateConfig_whenValidYamlFormatRequest_shouldReturnYamlConfiguration() throws Exception {

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
                .outputFormat(OutputFormat.YAML)
                .build();
        var configRequest = GenerateConfigRequestTestHelper.generateConfigRequest(requestProps);

        var result = mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(configRequest)))
                .andExpect(status().isOk())
                .andReturn();

        var response = result.getResponse().getContentAsString();

        Map<String, Object> root = yaml.load(response);
        assertThat(root).containsKeys(
                "spring",
                "server",
                "logging",
                "management"
        );

        Map<String, Object> spring = mapAt(root, "spring");
        assertThat(spring).containsKeys(
                "application",
                "profiles",
                "datasource",
                "jpa"
        );

        Map<String, Object> application = mapAt(spring, "application");
        assertThat(application.get("name")).isEqualTo("test-app");

        Map<String, Object> profiles = mapAt(spring, "profiles");
        assertThat(profiles.get("active")).isEqualTo("dev");

        Map<String, Object> datasource = mapAt(spring, "datasource");
        assertThat(datasource).containsKeys(
                "url",
                "username",
                "password",
                "hikari"
        );

        assertThat(datasource.get("url"))
                .isEqualTo("jdbc:postgresql://test-host:5555/test-db");
        assertThat(datasource.get("username")).isEqualTo("test-user");
        assertThat(datasource.get("password")).isEqualTo("password");

        Map<String, Object> hikari = mapAt(datasource, "hikari");
        assertThat(hikari.get("maximum-pool-size")).isEqualTo(20);
        assertThat(hikari.get("minimum-idle")).isEqualTo(5);
        assertThat(hikari.get("connection-timeout")).isEqualTo(20_000);

        Map<String, Object> jpa = mapAt(spring, "jpa");
        assertThat(jpa).containsKeys(
                "hibernate",
                "database",
                "show-sql",
                "open-in-view"
        );
        assertThat(jpa.get("show-sql")).isEqualTo(true);
        assertThat(jpa.get("open-in-view")).isEqualTo(true);

        Map<String, Object> hibernate = mapAt(jpa, "hibernate");
        assertThat(hibernate.get("ddl-auto")).isEqualTo("validate");

        Map<String, Object> server = mapAt(root, "server");
        assertThat(server.get("port")).isEqualTo(8080);

        Map<String, Object> servlet = mapAt(server, "servlet");
        assertThat(servlet.get("context-path")).isEqualTo("/api");
    }
}
