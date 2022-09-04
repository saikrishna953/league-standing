package com.api.football.leaguestanding.controller;

import com.api.football.leaguestanding.exception.InvalidRequestException;
import com.api.football.leaguestanding.model.LeagueStandingDocument;
import com.api.football.leaguestanding.model.LeagueStandingDocumentDataLinksInner;
import com.api.football.leaguestanding.model.LeagueStandingRequest;
import com.api.football.leaguestanding.service.LeagueStandingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("v1/leagues")
@Slf4j
public class LeagueStandingResource {

    private final LeagueStandingService leagueStandingService;

    @Autowired
    public LeagueStandingResource(LeagueStandingService leagueStandingService) {
        this.leagueStandingService = leagueStandingService;
    }

    @GetMapping
    public ResponseEntity<LeagueStandingDocument> getLeagueStanding(
            @RequestParam(value = "leagueId", required = false) String leagueId,
            @RequestParam(value = "countryName", required = false) String countryName,
            @RequestParam(value = "teamName", required = false) String teamName,
            HttpServletRequest httpServletRequest) throws JsonProcessingException {

        LeagueStandingRequest requestAttributes = LeagueStandingRequest.builder().leagueId(leagueId)
                .countryName(countryName).teamName(teamName).build();
        Optional.ofNullable(leagueId).orElseThrow(() -> new InvalidRequestException("leagueId is empty"));
        LeagueStandingDocument document = leagueStandingService.getLeagueStanding(requestAttributes);
        LeagueStandingDocumentDataLinksInner link = new LeagueStandingDocumentDataLinksInner();
        String reqUrl = httpServletRequest.getRequestURL().toString();
        String queryString = httpServletRequest.getQueryString();
        if (queryString != null) {
            reqUrl += "?" + queryString;
        }
        link.setHref(reqUrl);
        link.setRel("self");
        document.getData().addLinksItem(link);
        return new ResponseEntity<>(document, HttpStatus.OK);
    }
}
