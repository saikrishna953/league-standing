package com.api.football.leaguestanding.service;

import com.api.football.leaguestanding.config.LeagueStandingConfig;
import com.api.football.leaguestanding.exception.LeagueIdNotFoundException;
import com.api.football.leaguestanding.model.LeagueStanding;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {FootBallApiHttpClient.class, LeagueStandingConfig.class})
@TestPropertySource("classpath:application-test.yml")
public class FootBallApiHttpClientTest {

    @Autowired
    private FootBallApiHttpClient subject;

    @Test
    public void getTeamStandingsByLeagueId_isSuccessResponseReturned() throws JsonProcessingException {
        LeagueStanding[] leagueStandings = subject.getTeamStandingsByLeagueId("302");
        Assertions.assertThat(leagueStandings).isNotNull();
    }

    @Test
    public void getTeamStandingsByLeagueId_isUnSuccessResponseReturned() {
        Assertions.assertThatThrownBy(() -> subject.getTeamStandingsByLeagueId("30")).isInstanceOf(LeagueIdNotFoundException.class);
    }

    @Test
    public void getTeamStandingsFallBack_isSuccessResponseReturned() {
        LeagueStanding[] leagueStandings = subject.getTeamStandingsFallBack("302");
        Assertions.assertThat(leagueStandings).isNotNull();
    }

}
