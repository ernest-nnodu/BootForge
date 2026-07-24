package com.jackalcode.BootForge.mapper;

import com.jackalcode.BootForge.domain.enums.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestProps {

    private String applicationName;
    private String activeProfile;
    private Integer serverPort;
    private String contextPath;
    private DatabaseType databaseType;
    private String username;
    private String password;
    private String host;
    private String databaseName;
    private Integer databasePort;
    private DdlAuto ddlAuto;
    private Boolean showSql;
    private Boolean openInView;
    private String exposedEndpoints;
    private HealthShowDetails showHealthDetails;
    private Integer maximumPoolSize;
    private Integer minimumIdle;
    private Long connectionTimeout;
    private LogLevel rootLevel;
    private LogLevel springLevel;
    private OutputFormat outputFormat;
}
