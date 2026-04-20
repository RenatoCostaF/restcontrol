package com.restocontrol.restcontrol_api.infra.exceptions;

public class LoginAlreadyExistsException extends RuntimeException {
    public LoginAlreadyExistsException() {
        super("There is already an user registered with the informed login.");
    }
}