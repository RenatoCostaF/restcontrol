package com.restocontrol.restcontrol_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.restocontrol.restcontrol_api.entities.User;
import com.restocontrol.restcontrol_api.infra.exceptions.InvalidOrExpiredTokenException;
import com.restocontrol.restcontrol_api.infra.exceptions.TokenGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        logger.info("Iniciando geração de token para o login: {}", user.getLogin());
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("restcontrol-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);

            logger.info("Token gerado com sucesso para o login: {}", user.getLogin());
            return token;
        } catch (JWTCreationException e){
            logger.error("Falha ao gerar token para o login: {}", user.getLogin(), e);
            throw new TokenGenerationException("Error while generating token", e);
        }
    }

    public String validateToken(String token){
        logger.debug("Iniciando validação de token JWT");
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String subject = JWT.require(algorithm)
                    .withIssuer("restcontrol-api")
                    .build()
                    .verify(token)
                    .getSubject();
            if (subject == null || subject.isBlank()) {
                logger.warn("Token JWT validado sem subject associado");
                throw new InvalidOrExpiredTokenException();
            }
            logger.debug("Token JWT validado com sucesso para o login: {}", subject);
            return subject;
        } catch (JWTVerificationException e) {
            logger.warn("Falha na validação do token JWT: {}", e.getMessage());
            throw new InvalidOrExpiredTokenException();
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(4).toInstant(ZoneOffset.of("-03:00"));
    }
}
