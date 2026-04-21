package com.restocontrol.restcontrol_api.infra.exceptions;

public class InvalidOrExpiredTokenException extends RuntimeException {
    public InvalidOrExpiredTokenException() {
        super("The access token is invalid or has expired.");
    }
}
