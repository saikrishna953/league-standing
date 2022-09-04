package com.api.football.leaguestanding.exception;

public class LeagueIdNotFoundException extends RuntimeException {

    public LeagueIdNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
