package com.restocontrol.restcontrol_api.infra.exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("The informed email is invalid");
    }
}
