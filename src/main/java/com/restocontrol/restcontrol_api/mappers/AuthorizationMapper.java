package com.restocontrol.restcontrol_api.mappers;

import com.restocontrol.restcontrol_api.dtos.LoginResponseDTO;

public class AuthorizationMapper {

    public LoginResponseDTO toLoginResponseDTO(String token) {
        return new LoginResponseDTO(token);
    }
}
