package com.api.football.leaguestanding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class},
        scanBasePackages = "com.api.football.leaguestanding")
public class LeagueStandingApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeagueStandingApplication.class, args);
    }

}
