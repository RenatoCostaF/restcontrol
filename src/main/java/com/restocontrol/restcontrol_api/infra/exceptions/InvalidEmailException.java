package com.restocontrol.restcontrol_api.infra.exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("O email inserido é inválido");
    }
}
