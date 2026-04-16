package com.restocontrol.restcontrol_api.infra.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("There is already an user registered with the informed email.");
    }
}
