package com.api.football.leaguestanding.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MessageCodeTest {

    @Test
    public void testMessageCode() {
        Assertions.assertThat(MessageCode.getLogMessage(MessageCode.ERR_MISSING_LEAGUE_ID))
                .isEqualTo("logger_code:100, logger_message:Missing league id in the request");
    }
}
