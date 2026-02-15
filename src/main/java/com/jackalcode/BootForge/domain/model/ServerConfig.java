package com.jackalcode.BootForge.domain.model;

public record ServerConfig(
        Integer port,
        String contextPath) {

    public ServerConfig(Integer port, String contextPath) {

        int resolvedPort = port == null ? 8080 : port;

        if (resolvedPort < 1 || resolvedPort > 65535) {
            throw new IllegalArgumentException("Server port must be between 1 and 65535");
        }

        this.port = resolvedPort;
        this.contextPath = (contextPath == null || contextPath.isBlank()) ? "/" : contextPath;
    }
}
