package com.api.football.leaguestanding.bdd;

import com.api.football.leaguestanding.model.LeagueStandingDocument;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class SharedResponseData {

    private String leagueId;

    private ResponseEntity response;

}
