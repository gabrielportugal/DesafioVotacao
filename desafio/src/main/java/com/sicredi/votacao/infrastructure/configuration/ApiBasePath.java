package com.sicredi.votacao.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiBasePath {
    private final String basePath;

    public ApiBasePath(@Value("${api.version}") String version) {
        this.basePath = "/api/" + version;
    }

    public String getBasePath() {
        return basePath;
    }
}
