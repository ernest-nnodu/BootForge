package com.jackalcode.BootForge.formatter;

import com.jackalcode.BootForge.domain.model.ApplicationConfig;
import com.jackalcode.BootForge.domain.model.Configuration;
import com.jackalcode.BootForge.domain.model.DatabaseConfig;
import com.jackalcode.BootForge.formatter.util.FormatterUtil;

public class YamlFormatter implements ConfigFormatter {

    @Override
    public String format(Configuration configuration) {

        return """
                spring:
                  %s
                 \s
                  %s
               \s"""
                .formatted(
                        formatApplicationConfig(configuration.applicationConfig()),
                        formatDatabaseConfig(configuration.databaseConfig())
                );
    }

    private String formatApplicationConfig(ApplicationConfig applicationConfig) {

        return """
                  application:
                    name:%s
                   \s
                  profiles:
                    active:%s
                   \s
               \s"""
                .formatted(
                        applicationConfig.applicationName(),
                        applicationConfig.activeProfile()
                );
    }

    private String formatDatabaseConfig(DatabaseConfig databaseConfig) {

        return """
                  datasource:
                    url:%s
                    username:%s
                    password:%s
                   \s
               \s"""
                .formatted(
                        FormatterUtil.generateDatasourceUrl(
                                databaseConfig.databaseType(),
                                databaseConfig.databaseName(),
                                databaseConfig.host(),
                                databaseConfig.port()
                        ),
                        databaseConfig.username(),
                        databaseConfig.password()
                );
    }
}
