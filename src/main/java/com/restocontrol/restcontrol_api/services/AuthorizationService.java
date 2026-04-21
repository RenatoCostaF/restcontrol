package com.restocontrol.restcontrol_api.services;

import com.restocontrol.restcontrol_api.dtos.AuthenticationDTO;
import com.restocontrol.restcontrol_api.dtos.LoginResponseDTO;
import com.restocontrol.restcontrol_api.entities.User;
import com.restocontrol.restcontrol_api.infra.exceptions.AuthenticationFailedException;
import com.restocontrol.restcontrol_api.infra.security.TokenService;
import com.restocontrol.restcontrol_api.mappers.AuthorizationMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AuthorizationMapper authorizationMapper;

    public AuthorizationService(AuthenticationManager authenticationManager, TokenService tokenService, AuthorizationMapper authorizationMapper) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.authorizationMapper = authorizationMapper;
    }

    public LoginResponseDTO login(AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        try{
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return authorizationMapper.toLoginResponseDTO(token);
        } catch (AuthenticationException e) {
            throw new AuthenticationFailedException();
        }
    }
}