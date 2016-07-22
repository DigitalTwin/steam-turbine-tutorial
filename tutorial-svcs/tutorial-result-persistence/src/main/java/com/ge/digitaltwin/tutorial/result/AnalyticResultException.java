package com.ge.digitaltwin.tutorial.result;

@SuppressWarnings("unused")
public class AnalyticResultException extends RuntimeException {

    private static final long serialVersionUID = 3664450720293958780L;

    public AnalyticResultException(String message) {
        super(message);
    }

    public AnalyticResultException(String message, Throwable cause) {
        super(message, cause);
    }

}
