package com.api.football.leaguestanding.service;

import com.api.football.leaguestanding.exception.LeagueIdNotFoundException;
import com.api.football.leaguestanding.model.LeagueStanding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class FootBallApiHttpClient {

    @Value("${apifootball.base.url}")
    private String baseUrl;

    @Value("${apifootball.apikey}")
    private String apiKey;

    private static final String KEY = "APIkey";
    private static final String ACTION = "action";
    private static final String LEAGUE_ID = "league_id";
    private static final String STANDINGS_ACTION = "get_standings";

    private final RestTemplate restTemplate;

    @Autowired
    public FootBallApiHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "getStandings", fallbackMethod = "getTeamStandingsFallBack")
    public LeagueStanding[] getTeamStandingsByLeagueId(String leagueId) throws JsonProcessingException {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(ACTION, STANDINGS_ACTION);
        queryParams.put(LEAGUE_ID, leagueId);
        UriComponentsBuilder builder = getUriComponentsBuilder(baseUrl, queryParams);
        JsonNode response = this.restTemplate
                .exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeaders()), JsonNode.class)
                .getBody();
        JsonNode error = response.get("error");
        if (!ObjectUtils.isEmpty(error)) {
            Optional.of(error).map(JsonNode::asText).filter(String.valueOf(HttpStatus.NOT_FOUND.value())::equals)
                    .map(s -> {
                        throw new LeagueIdNotFoundException("leagueId=" + leagueId);
                    });
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.treeToValue(response, LeagueStanding[].class);
    }

    public LeagueStanding[] getTeamStandingsFallBack(String leagueId) {
        LeagueStanding teamStanding = new LeagueStanding();
        teamStanding.setLeagueId(leagueId);
        return new LeagueStanding[]{teamStanding};
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    private UriComponentsBuilder getUriComponentsBuilder(String url, Map<String, String> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam(KEY, apiKey);
        queryParams.forEach(builder::queryParam);
        return builder;
    }
}
