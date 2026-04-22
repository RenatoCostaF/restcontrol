package com.restocontrol.restcontrol_api.infra.exceptions;

import java.util.List;
import java.util.Map;

public class DtoValidationException extends RuntimeException {

    private final Map<String, List<String>> fieldErrors;

    public DtoValidationException(Map<String, List<String>> fieldErrors) {
        super("One or more DTO fields are invalid.");
        this.fieldErrors = fieldErrors;
    }

    public Map<String, List<String>> getFieldErrors(){
        return fieldErrors;
    }
}
