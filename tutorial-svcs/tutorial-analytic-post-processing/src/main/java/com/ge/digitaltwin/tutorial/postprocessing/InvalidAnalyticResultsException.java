package com.ge.digitaltwin.tutorial.postprocessing;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class InvalidAnalyticResultsException extends RuntimeException {

    public InvalidAnalyticResultsException(String message, Exception cause) {
        super(message, cause);
    }

    public InvalidAnalyticResultsException(String message) {
        super(message);
    }
}
