package com.api.football.leaguestanding.exception;

import com.api.football.leaguestanding.common.MessageCode;
import com.api.football.leaguestanding.model.LeagueStandingDocument;
import com.api.football.leaguestanding.model.LeagueStandingDocumentErrorsInner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    protected ResponseEntity<LeagueStandingDocument> handleInvalidRequestException(
            InvalidRequestException ex) {
        log.error(MessageCode.getLogMessage(MessageCode.ERR_MISSING_LEAGUE_ID), ex);

        LeagueStandingDocument leagueStandingDocument =
                new LeagueStandingDocument();
        leagueStandingDocument.setErrors(buildErrorDocument(HttpStatus.BAD_REQUEST, MessageCode.ERR_MISSING_LEAGUE_ID
                , ex.getMessage()));
        return new ResponseEntity<>(leagueStandingDocument, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(LeagueIdNotFoundException.class)
    protected ResponseEntity<LeagueStandingDocument> handleInvalidRequestException(
            LeagueIdNotFoundException ex) {
        log.error(MessageCode.getLogMessage(MessageCode.ERR_LEAGUE_ID_NOT_FOUND), ex);

        LeagueStandingDocument leagueStandingDocument =
                new LeagueStandingDocument();
        leagueStandingDocument.setErrors(buildErrorDocument(HttpStatus.NOT_FOUND, MessageCode.ERR_LEAGUE_ID_NOT_FOUND
                , ex.getMessage()));
        return new ResponseEntity<>(leagueStandingDocument, HttpStatus.NOT_FOUND);

    }

    private List<LeagueStandingDocumentErrorsInner> buildErrorDocument(HttpStatus status,
                                                                       MessageCode messageCode,
                                                                       String detail) {

        List<LeagueStandingDocumentErrorsInner> leagueStandingDocumentErrorsInners
                = new ArrayList<>();
        LeagueStandingDocumentErrorsInner leagueStandingDocumentErrorsInner =
                new LeagueStandingDocumentErrorsInner();
        leagueStandingDocumentErrorsInner.setId(UUID.randomUUID().toString());
        leagueStandingDocumentErrorsInner.setCode(messageCode.getCode());
        leagueStandingDocumentErrorsInner.setTitle(messageCode.getStaticMessage());
        leagueStandingDocumentErrorsInner.setStatus(String.valueOf(status.value()));
        leagueStandingDocumentErrorsInner.setDetail(detail);
        leagueStandingDocumentErrorsInners.add(leagueStandingDocumentErrorsInner);
        return leagueStandingDocumentErrorsInners;
    }
}
