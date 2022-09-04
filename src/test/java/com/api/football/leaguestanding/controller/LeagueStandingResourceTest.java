package com.api.football.leaguestanding.controller;

import com.api.football.leaguestanding.exception.InvalidRequestException;
import com.api.football.leaguestanding.model.LeagueStandingDocument;
import com.api.football.leaguestanding.model.LeagueStandingDocumentData;
import com.api.football.leaguestanding.service.LeagueStandingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class LeagueStandingResourceTest {

    @InjectMocks
    private LeagueStandingResource subject;

    @Mock
    private LeagueStandingService leagueStandingService;

    @Mock
    private HttpServletRequest httpServletRequest;

    private LeagueStandingDocument leagueStandingDocument;

    private static final String LEAGUE_ID = "302";

    @BeforeEach
    public void setup() {
        leagueStandingDocument = new LeagueStandingDocument();
        LeagueStandingDocumentData data = new LeagueStandingDocumentData();
        data.setId(LEAGUE_ID);
        leagueStandingDocument.setData(data);
    }

    @Test
    public void getLeagueStanding_isResponsePopulated_ifSuccessful() throws JsonProcessingException {
        Mockito.when(leagueStandingService.getLeagueStanding(Mockito.any()))
                .thenReturn(leagueStandingDocument);
        Mockito.when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/leaguestanding/v1/leagues"));
        Mockito.when(httpServletRequest.getQueryString()).thenReturn(String.valueOf(new StringBuffer("leagueId=" + LEAGUE_ID)));
        ResponseEntity<LeagueStandingDocument> response =
                subject.getLeagueStanding(LEAGUE_ID, null, null, httpServletRequest);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getData().getId()).isEqualTo(LEAGUE_ID);
    }

    @Test
    public void getLeagueStanding_isResponsePopulated_ifUnSuccessful() {
        Assertions.assertThatThrownBy(() -> subject.getLeagueStanding("", null, null, httpServletRequest))
                .isInstanceOf(InvalidRequestException.class);

    }

}
