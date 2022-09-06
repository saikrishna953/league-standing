package com.api.football.leaguestanding.bdd.steps;

import com.api.football.leaguestanding.bdd.CucumberBootstrap;
import com.api.football.leaguestanding.bdd.SharedRequestData;
import com.api.football.leaguestanding.bdd.SharedResponseData;
import com.api.football.leaguestanding.model.LeagueStandingDocument;
import com.api.football.leaguestanding.model.LeagueStandingRequest;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Slf4j
public class GetLeagueStandingSteps extends CucumberBootstrap implements En {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    SharedRequestData sharedRequestData;

    @Autowired
    SharedResponseData sharedResponseData;

    public GetLeagueStandingSteps() {

        When("client sends a request to league standing microservice for health check",
                () -> {

                    ResponseEntity response =
                            testRestTemplate.getForEntity("/actuator/health", Map.class);
                    sharedResponseData.setResponse(response);

                });

        Then("^client should receive response with http status as (\\d+)$", (Integer status) -> {
            log.info("Response status-" + sharedResponseData.getResponse().getStatusCode());
            log.info("Response body-" + sharedResponseData.getResponse().getBody());
            Assertions.assertThat(sharedResponseData.getResponse().getStatusCode().value()).isEqualTo(status);
        });
        And("league standing microservice should return health check state as {string}", (String state) -> {
            Assertions.assertThat(sharedResponseData.getResponse().getBody().toString()).isEqualTo("{status=UP}");
        });
        When("client submits getLeagueStanding API with {string}, {string} and {string}", (String leagueId,
                                                                                           String countryName,
                                                                                           String teamName) -> {
            sharedRequestData.setRequest(LeagueStandingRequest.builder().leagueId(leagueId).countryName(countryName)
                    .teamName(teamName).build());
            ResponseEntity response =
                    testRestTemplate.getForEntity("/v1/leagues" + "?leagueId=" + leagueId + "&countryName=" + countryName
                            + "&teamName=", LeagueStandingDocument.class);
            sharedResponseData.setLeagueId(leagueId);
            sharedResponseData.setResponse(response);
        });
        And("getLeagueStanding API should respond with corresponding overall league positions for the given input", () -> {

            LeagueStandingDocument response = (LeagueStandingDocument) sharedResponseData.getResponse().getBody();
            Assertions.assertThat(response.getData()).isNotNull();
            Assertions.assertThat(response.getData().getId()).isEqualTo(sharedResponseData.getLeagueId());
        });
        Then("getLeagueStanding API returns an error response back to client with status as {string}, code as {string}, title as {string}, detail as {string}",
                (String status, String code, String title, String detail) -> {
                    log.info("Response status-" + sharedResponseData.getResponse().getStatusCode());
                    log.info("Response body-" + sharedResponseData.getResponse().getBody());
                    LeagueStandingDocument response = (LeagueStandingDocument) sharedResponseData.getResponse().getBody();
                    Assertions.assertThat(response.getData()).isNull();
                    Assertions.assertThat(response.getErrors()).isNotNull();
                    Assertions.assertThat(response.getErrors().get(0).getStatus()).isEqualTo(status);
                    Assertions.assertThat(response.getErrors().get(0).getCode()).isEqualTo(code);
                    Assertions.assertThat(response.getErrors().get(0).getTitle()).isEqualTo(title);
                    Assertions.assertThat(response.getErrors().get(0).getDetail()).isEqualTo(detail);
                });
    }

}
