package com.restocontrol.restcontrol_api.infra.exceptions;

public class SenhaIncorretaException extends RuntimeException {
    public SenhaIncorretaException() {
        super("A senha está incorreta");
    }
}
