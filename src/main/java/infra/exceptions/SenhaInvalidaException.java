package com.restocontrol.restcontrol_api.infra.exceptions;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("A senha precisa conter pelo menos 8 caracteres");
    }
}
