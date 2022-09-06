package com.api.football.leaguestanding.bdd;

import com.api.football.leaguestanding.LeagueStandingApplication;
import com.api.football.leaguestanding.bdd.config.StepDefsConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = {LeagueStandingApplication.class, StepDefsConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
@TestPropertySource("classpath:application-test.yml")
public class CucumberBootstrap {

}
