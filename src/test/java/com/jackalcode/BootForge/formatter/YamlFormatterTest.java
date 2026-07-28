package com.jackalcode.BootForge.formatter;

import com.jackalcode.BootForge.common.ConfigurationTestHelper;
import com.jackalcode.BootForge.common.GenerateConfigRequestTestHelper;
import com.jackalcode.BootForge.common.RequestProps;
import com.jackalcode.BootForge.domain.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

import static com.jackalcode.BootForge.common.YamlResponseTestHelper.mapAt;
import static org.assertj.core.api.Assertions.assertThat;

public class YamlFormatterTest {

    private YamlFormatter yamlFormatter;
    private Yaml yaml;

    @BeforeEach
    void setUp() {
        yamlFormatter = new YamlFormatter();
        yaml = new Yaml();
    }

    @Test
    @DisplayName("format should return formatted yaml")
    void format_whenCalled_shouldReturnFormattedYaml() {

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

        var result = yamlFormatter.format(configuration);

        Map<String, Object> root = yaml.load(result);
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
