package com.restocontrol.restcontrol_api.infra.exceptions;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
        super("A senha está incorreta");
    }
}
