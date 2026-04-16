package com.restocontrol.restcontrol_api.infra.exceptions;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
        super("The password is incorrect");
    }
}
