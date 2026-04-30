package com.restocontrol.restcontrol_api.services;

import com.restocontrol.restcontrol_api.dtos.AuthenticationDTO;
import com.restocontrol.restcontrol_api.dtos.LoginResponseDTO;
import com.restocontrol.restcontrol_api.entities.User;
import com.restocontrol.restcontrol_api.infra.exceptions.AuthenticationFailedException;
import com.restocontrol.restcontrol_api.infra.security.TokenService;
import com.restocontrol.restcontrol_api.mappers.AuthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AuthMapper authMapper;

    public AuthService(AuthenticationManager authenticationManager, TokenService tokenService, AuthMapper authMapper) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.authMapper = authMapper;
    }

    public LoginResponseDTO login(AuthenticationDTO data) {
        logger.info("Iniciando autenticação para o login: {}", data.login());
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        try{
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            logger.info("Autenticação realizada com sucesso para o login: {}", data.login());
            return authMapper.toLoginResponseDTO(token);
        } catch (AuthenticationException e) {
            logger.warn("Falha na autenticação para o login: {}", data.login());
            logger.debug("Detalhes da falha de autenticação para o login {}: {}", data.login(), e.getMessage(), e);
            throw new AuthenticationFailedException();
        }
    }
}
