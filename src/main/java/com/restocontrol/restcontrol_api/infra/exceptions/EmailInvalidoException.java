package com.restocontrol.restcontrol_api.infra.exceptions;

public class EmailInvalidoException extends RuntimeException {
    public EmailInvalidoException() {
        super("O email inserido é inválido");
    }
}
