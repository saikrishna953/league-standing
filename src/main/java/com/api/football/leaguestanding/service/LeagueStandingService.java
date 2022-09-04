package com.api.football.leaguestanding.service;

import com.api.football.leaguestanding.model.LeagueStandingDocument;
import com.api.football.leaguestanding.model.LeagueStandingRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface LeagueStandingService {

    LeagueStandingDocument getLeagueStanding(LeagueStandingRequest leagueStandingRequest) throws JsonProcessingException;
}
