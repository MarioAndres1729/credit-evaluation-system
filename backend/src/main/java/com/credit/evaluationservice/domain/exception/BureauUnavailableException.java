package com.credit.evaluationservice.domain.exception;

public class BureauUnavailableException extends RuntimeException {

    public BureauUnavailableException(String message) {
        super(message);
    }

    public BureauUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}

