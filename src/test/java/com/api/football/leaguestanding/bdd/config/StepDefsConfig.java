package com.api.football.leaguestanding.bdd.config;

import com.api.football.leaguestanding.bdd.SharedRequestData;
import com.api.football.leaguestanding.bdd.SharedResponseData;
import io.cucumber.spring.CucumberTestContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class StepDefsConfig {
    @Bean
    @Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
    public SharedRequestData sharedRequestData() {
        return new SharedRequestData();
    }

    @Bean
    @Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
    public SharedResponseData sharedResponseData() {
        return new SharedResponseData();
    }
}
