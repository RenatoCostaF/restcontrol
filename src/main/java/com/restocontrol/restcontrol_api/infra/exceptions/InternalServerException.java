package com.restocontrol.restcontrol_api.infra.exceptions;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(Throwable cause) {
      super(cause);
    }

    public InternalServerException (String message, Throwable cause) {
      super(message, cause);
    }
}
