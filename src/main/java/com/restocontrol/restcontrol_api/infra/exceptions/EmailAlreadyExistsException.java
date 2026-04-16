package com.restocontrol.restcontrol_api.infra.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("Já existe um usuário cadastrado com o email informado");
    }
}
