package com.api.football.leaguestanding.common;

import lombok.Getter;

@Getter
public enum MessageCode {

    ERR_MISSING_LEAGUE_ID("100", "Missing league id in the request"),
    ERR_LEAGUE_ID_NOT_FOUND("101", "League id not found");

    private String code;

    private String staticMessage;

    MessageCode(String code, String staticMessage) {
        this.code = code;
        this.staticMessage = staticMessage;
    }

    public static String getLogMessage(MessageCode messageCode) {
        return "logger_code:" + messageCode.getCode() + ", " + "logger_message:" + messageCode.getStaticMessage();
    }
}
