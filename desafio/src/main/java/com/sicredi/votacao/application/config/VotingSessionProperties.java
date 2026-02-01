package com.sicredi.votacao.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "votingsession")
public class VotingSessionProperties {

    private Integer defaultDurationMinutes = 1;

    public Integer getDefaultDurationMinutes() {
        return defaultDurationMinutes;
    }

}
