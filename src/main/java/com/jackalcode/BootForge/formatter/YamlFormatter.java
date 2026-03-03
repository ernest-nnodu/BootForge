package com.jackalcode.BootForge.formatter;

import com.jackalcode.BootForge.domain.model.*;
import com.jackalcode.BootForge.formatter.util.FormatterUtil;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class YamlFormatter implements ConfigFormatter {

    private static final String INDENT = "  ";

    @Override
    public String format(Configuration configuration) {
        Map<String, Object> yamlStructure = toYamlMap(configuration);

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        options.setIndent(2);

        Yaml yaml = new Yaml(options);

        return yaml.dump(yamlStructure);
    }

    private Map<String, Object> toYamlMap(Configuration config) {

        Map<String, Object> root = new LinkedHashMap<>();

        // ---- SPRING ----
        Map<String, Object> spring = new LinkedHashMap<>();
        root.put("spring", spring);

        // Application
        Map<String, Object> application = new LinkedHashMap<>();
        application.put("name", config.applicationConfig().applicationName());

        Map<String, Object> profiles = new LinkedHashMap<>();
        profiles.put("active", config.applicationConfig().activeProfile());

        spring.put("application", application);
        spring.put("profiles", profiles);

        // Datasource
        Map<String, Object> datasource = new LinkedHashMap<>();
        datasource.put("url",
                FormatterUtil.generateDatasourceUrl(
                        config.databaseConfig().databaseType(),
                        config.databaseConfig().databaseName(),
                        config.databaseConfig().host(),
                        config.databaseConfig().port()
                )
        );
        datasource.put("username", config.databaseConfig().username());
        datasource.put("password", config.databaseConfig().password());

        // Hikari
        Map<String, Object> hikari = new LinkedHashMap<>();
        hikari.put("maximum-pool-size", config.hikariConfig().maximumPoolSize());
        hikari.put("minimum-idle", config.hikariConfig().minimumIdle());
        hikari.put("connection-timeout", config.hikariConfig().connectionTimeout());

        datasource.put("hikari", hikari);
        spring.put("datasource", datasource);

        // JPA
        Map<String, Object> jpa = new LinkedHashMap<>();
        Map<String, Object> hibernate = new LinkedHashMap<>();
        hibernate.put("ddl-auto", config.jpaConfig().ddlAuto().toString().toLowerCase());

        Map<String, Object> database = new LinkedHashMap<>();
        database.put("platform",
                FormatterUtil.generateSQLDialect(config.databaseConfig().databaseType()));

        jpa.put("hibernate", hibernate);
        jpa.put("database", database);
        jpa.put("show-sql", config.jpaConfig().showSql());
        jpa.put("open-in-view", config.jpaConfig().openInView());

        spring.put("jpa", jpa);

        // ---- SERVER ----
        Map<String, Object> server = new LinkedHashMap<>();
        server.put("port", config.serverConfig().port());

        Map<String, Object> servlet = new LinkedHashMap<>();
        servlet.put("context-path", config.serverConfig().contextPath());
        server.put("servlet", servlet);

        root.put("server", server);

        // ---- LOGGING ----
        Map<String, Object> logging = new LinkedHashMap<>();
        Map<String, Object> level = new LinkedHashMap<>();
        level.put("root", config.loggingConfig().rootLevel().toString().toLowerCase());
        level.put("org.springframework", config.loggingConfig().springLevel().toString().toLowerCase());
        logging.put("level", level);

        root.put("logging", logging);

        // ---- ACTUATOR ----
        Map<String, Object> management = new LinkedHashMap<>();
        Map<String, Object> endpoints = new LinkedHashMap<>();
        Map<String, Object> web = new LinkedHashMap<>();
        Map<String, Object> exposure = new LinkedHashMap<>();

        exposure.put("include", config.actuatorConfig().exposedEndpoints());
        web.put("exposure", exposure);
        endpoints.put("web", web);

        Map<String, Object> endpoint = new LinkedHashMap<>();
        Map<String, Object> health = new LinkedHashMap<>();
        health.put("show-details", config.actuatorConfig().showHealthDetails().toString().toLowerCase());

        endpoint.put("health", health);

        management.put("endpoints", endpoints);
        management.put("endpoint", endpoint);

        root.put("management", management);

        return root;
    }

}
