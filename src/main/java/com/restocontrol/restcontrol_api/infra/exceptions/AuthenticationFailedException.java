package com.restocontrol.restcontrol_api.infra.exceptions;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException() {
        super("Invalid username or password");
    }
}
