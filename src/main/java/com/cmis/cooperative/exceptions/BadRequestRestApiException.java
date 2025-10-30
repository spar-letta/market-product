package com.cmis.cooperative.exceptions;

public class BadRequestRestApiException extends Exception {
    public BadRequestRestApiException(String message) {
        super(message);
    }
}
