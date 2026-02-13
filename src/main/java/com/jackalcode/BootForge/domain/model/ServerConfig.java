package com.jackalcode.BootForge.domain.model;

public record ServerConfig(
        int port,
        String contextPath) {

    public ServerConfig(int port, String contextPath) {

        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Server port must be between 1 and 65535");
        } else {
            this.port = port;
        }

        this.contextPath = (contextPath == null || contextPath.isBlank()) ? "/" : contextPath;
    }
}
