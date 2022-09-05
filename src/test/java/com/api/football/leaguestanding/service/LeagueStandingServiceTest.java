package com.api.football.leaguestanding.service;

import com.api.football.leaguestanding.model.LeagueStanding;
import com.api.football.leaguestanding.model.LeagueStandingDocument;
import com.api.football.leaguestanding.model.LeagueStandingRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LeagueStandingServiceTest {

    @InjectMocks
    private LeagueStandingServiceImpl subject;

    @Mock
    private FootBallApiHttpClient footBallApiHttpClient;

    private LeagueStanding[] leagueStandings;

    @BeforeEach
    public void setup() throws JsonProcessingException {
        LeagueStanding leagueStanding1 = new LeagueStanding();
        leagueStanding1.setLeagueId("302");
        leagueStanding1.setOverallLeaguePosition("1");
        leagueStanding1.setCountryName("Spain");
        leagueStanding1.setTeamName("Real Madrid");
        leagueStanding1.setTeamId("22");

        LeagueStanding leagueStanding2 = new LeagueStanding();
        leagueStanding2.setLeagueId("302");
        leagueStanding2.setOverallLeaguePosition("2");
        leagueStanding2.setCountryName("Spain");
        leagueStanding2.setTeamName("La Liga");
        leagueStanding2.setTeamId("23");

        leagueStandings = new LeagueStanding[]{leagueStanding1, leagueStanding2};
        Mockito.when(footBallApiHttpClient.getTeamStandingsByLeagueId(Mockito.any())).thenReturn(leagueStandings);

    }

    @Test
    public void getLeagueStanding_ifLeagueIdIsAvailableInRequest_isLeagueStandingDocumentReturned() throws JsonProcessingException {
        LeagueStandingRequest request = LeagueStandingRequest.builder().leagueId("302").build();
        LeagueStandingDocument result = subject.getLeagueStanding(request);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getData().getAttributes().size()).isEqualTo(2);
        Assertions.assertThat(result.getData().getId()).isEqualTo("302");
        Assertions.assertThat(result.getData().getAttributes().get(0).getOverallLeaguePosition()).isEqualTo("1");
        Assertions.assertThat(result.getData().getAttributes().get(0).getCountryName()).isEqualTo("Spain");
        Assertions.assertThat(result.getData().getAttributes().get(0).getTeamName()).isEqualTo("Real Madrid");
        Assertions.assertThat(result.getData().getAttributes().get(0).getTeamId()).isEqualTo("22");

        Assertions.assertThat(result.getData().getId()).isEqualTo("302");
        Assertions.assertThat(result.getData().getAttributes().get(1).getOverallLeaguePosition()).isEqualTo("2");
        Assertions.assertThat(result.getData().getAttributes().get(1).getCountryName()).isEqualTo("Spain");
        Assertions.assertThat(result.getData().getAttributes().get(1).getTeamName()).isEqualTo("La Liga");
        Assertions.assertThat(result.getData().getAttributes().get(1).getTeamId()).isEqualTo("23");
    }

    @Test
    public void getLeagueStanding_ifLeagueIdAndCountryNameIsAvailable_isLeagueStandingDocumentReturned() throws JsonProcessingException {
        LeagueStandingRequest request = LeagueStandingRequest.builder().leagueId("302")
                .countryName("Spain").build();
        LeagueStandingDocument result = subject.getLeagueStanding(request);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getData().getAttributes().size()).isEqualTo(2);
        Assertions.assertThat(result.getData().getId()).isEqualTo("302");
        Assertions.assertThat(result.getData().getAttributes().get(0).getOverallLeaguePosition()).isEqualTo("1");
        Assertions.assertThat(result.getData().getAttributes().get(0).getCountryName()).isEqualTo("Spain");
        Assertions.assertThat(result.getData().getAttributes().get(0).getTeamName()).isEqualTo("Real Madrid");
        Assertions.assertThat(result.getData().getAttributes().get(0).getTeamId()).isEqualTo("22");

        Assertions.assertThat(result.getData().getId()).isEqualTo("302");
        Assertions.assertThat(result.getData().getAttributes().get(1).getOverallLeaguePosition()).isEqualTo("2");
        Assertions.assertThat(result.getData().getAttributes().get(1).getCountryName()).isEqualTo("Spain");
        Assertions.assertThat(result.getData().getAttributes().get(1).getTeamName()).isEqualTo("La Liga");
        Assertions.assertThat(result.getData().getAttributes().get(1).getTeamId()).isEqualTo("23");
    }

    @Test
    public void getLeagueStanding_ifLeagueIdAndTeamNameIsAvailable_isLeagueStandingDocumentReturned() throws JsonProcessingException {
        LeagueStandingRequest request = LeagueStandingRequest.builder().leagueId("302")
                .teamName("Real Madrid").build();
        LeagueStandingDocument result = subject.getLeagueStanding(request);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getData().getAttributes().size()).isEqualTo(1);
        Assertions.assertThat(result.getData().getId()).isEqualTo("302");
        Assertions.assertThat(result.getData().getAttributes().get(0).getOverallLeaguePosition()).isEqualTo("1");
        Assertions.assertThat(result.getData().getAttributes().get(0).getCountryName()).isEqualTo("Spain");
        Assertions.assertThat(result.getData().getAttributes().get(0).getTeamName()).isEqualTo("Real Madrid");
        Assertions.assertThat(result.getData().getAttributes().get(0).getTeamId()).isEqualTo("22");

    }

    @Test
    public void getLeagueStanding_ifLeagueIdAndCountryNameAndTeamNameIsAvailable_isLeagueStandingDocumentReturned() throws JsonProcessingException {
        LeagueStandingRequest request = LeagueStandingRequest.builder().leagueId("302")
                .countryName("Spain").teamName("Real Madrid").build();
        LeagueStandingDocument result = subject.getLeagueStanding(request);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getData().getAttributes().size()).isEqualTo(1);
        Assertions.assertThat(result.getData().getId()).isEqualTo("302");
        Assertions.assertThat(result.getData().getAttributes().get(0).getOverallLeaguePosition()).isEqualTo("1");
        Assertions.assertThat(result.getData().getAttributes().get(0).getCountryName()).isEqualTo("Spain");
        Assertions.assertThat(result.getData().getAttributes().get(0).getTeamName()).isEqualTo("Real Madrid");
        Assertions.assertThat(result.getData().getAttributes().get(0).getTeamId()).isEqualTo("22");
    }
}
