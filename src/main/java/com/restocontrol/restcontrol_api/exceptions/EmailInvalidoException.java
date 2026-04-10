package com.restocontrol.restcontrol_api.exceptions;

public class EmailInvalidoException extends RuntimeException {
    public EmailInvalidoException() {
        super("O email inserido é inválido");
    }
}
