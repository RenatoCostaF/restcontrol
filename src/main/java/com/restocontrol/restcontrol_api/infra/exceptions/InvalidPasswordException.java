package com.restocontrol.restcontrol_api.infra.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("The password must have at least 6 characters");
    }
}
