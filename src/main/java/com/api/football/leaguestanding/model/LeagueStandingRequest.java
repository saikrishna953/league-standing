package com.api.football.leaguestanding.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LeagueStandingRequest {

    String leagueId;

    String countryName;

    String teamName;
}
