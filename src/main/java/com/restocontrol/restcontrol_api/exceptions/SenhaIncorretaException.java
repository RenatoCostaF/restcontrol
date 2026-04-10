package com.restocontrol.restcontrol_api.exceptions;

public class SenhaIncorretaException extends RuntimeException {
    public SenhaIncorretaException() {
        super("A senha está incorreta");
    }
}
