package com.restocontrol.restcontrol_api.services;

import com.restocontrol.restcontrol_api.infra.exceptions.EmailInvalidoException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserValidationService {

    private static final Pattern EMAIL_PATERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public void validarEmail(String email) {
        if (!EMAIL_PATERN.matcher(email).matches()) {
            throw new EmailInvalidoException();
        }
    }
}
