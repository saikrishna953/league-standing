package com.api.football.leaguestanding.service;

import com.api.football.leaguestanding.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LeagueStandingServiceImpl implements LeagueStandingService {

    private final FootBallApiHttpClient footBallApiHttpClient;

    @Autowired
    public LeagueStandingServiceImpl(FootBallApiHttpClient footBallApiHttpClient) {
        this.footBallApiHttpClient = footBallApiHttpClient;
    }

    @Override
    public LeagueStandingDocument getLeagueStanding(LeagueStandingRequest request) throws JsonProcessingException {

        LeagueStanding[] leagueStandings = footBallApiHttpClient.getTeamStandingsByLeagueId(request.getLeagueId());

        LeagueStandingDocument document = new LeagueStandingDocument();
        LeagueStandingDocumentData data = new LeagueStandingDocumentData();
        data.setId(request.getLeagueId());
        List<LeagueStandingDocumentDataAttributesInner> attributes = new ArrayList<>();
        List<LeagueStanding> leagueStandingListByLeagueIdAndCountryName = null;
        if (StringUtils.isNotEmpty(request.getCountryName())) {
            leagueStandingListByLeagueIdAndCountryName = Arrays.stream(leagueStandings)
                    .filter(leagueStanding -> leagueStanding.getCountryName().equals(request.getCountryName()))
                    .collect(Collectors.toList());
        }
        List<LeagueStanding> leagueStandingListByLeagueIdAndCountryNameAndTeamName = null;
        List<LeagueStanding> leagueStandingListByLeagueIdAndTeamName = null;
        if (StringUtils.isNotEmpty(request.getTeamName())) {
            if (!CollectionUtils.isEmpty(leagueStandingListByLeagueIdAndCountryName)) {
                leagueStandingListByLeagueIdAndCountryNameAndTeamName = leagueStandingListByLeagueIdAndCountryName.stream()
                        .filter(leagueStanding -> leagueStanding.getTeamName().equals(request.getTeamName()))
                        .collect(Collectors.toList());
            } else {
                leagueStandingListByLeagueIdAndTeamName = Arrays.stream(leagueStandings)
                        .filter(leagueStanding -> leagueStanding.getTeamName().equals(request.getTeamName()))
                        .collect(Collectors.toList());
            }
        }


        if (!CollectionUtils.isEmpty(leagueStandingListByLeagueIdAndCountryNameAndTeamName)) {
            mapToAttributes(leagueStandingListByLeagueIdAndCountryNameAndTeamName, attributes);
        } else if (!CollectionUtils.isEmpty(leagueStandingListByLeagueIdAndCountryName)) {
            mapToAttributes(leagueStandingListByLeagueIdAndCountryName, attributes);
        } else if (!CollectionUtils.isEmpty(leagueStandingListByLeagueIdAndTeamName)) {
            mapToAttributes(leagueStandingListByLeagueIdAndTeamName, attributes);
        } else {
            mapToAttributes(List.of(leagueStandings), attributes);
        }

        data.setAttributes(attributes);
        document.setData(data);
        return document;

    }

    private void mapToAttributes(List<LeagueStanding> leagueStandings, List<LeagueStandingDocumentDataAttributesInner> attributes) {
        leagueStandings.stream().forEach(leagueStanding -> {
            LeagueStandingDocumentDataAttributesInner attribute = new LeagueStandingDocumentDataAttributesInner();
            attribute.setLeagueId(leagueStanding.getLeagueId());
            attribute.setLeagueName(leagueStanding.getLeagueName());
            attribute.setCountryName(leagueStanding.getCountryName());
            attribute.setTeamId(leagueStanding.getTeamId());
            attribute.setTeamName(leagueStanding.getTeamName());
            attribute.setOverallLeaguePosition(leagueStanding.getOverallLeaguePosition());
            attributes.add(attribute);
        });
    }
}
