package com.api.football.leaguestanding.exception;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String errorMessage) {
        super(errorMessage);
    }
}
