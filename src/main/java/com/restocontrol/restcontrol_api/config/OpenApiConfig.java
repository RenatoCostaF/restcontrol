package com.restocontrol.restcontrol_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI locaTech(){
        return new OpenAPI()
                .info(
                        new Info().title("Gestao Usuarios - Restaurantes")
                                .description("Projeto desenvolvido para o Tech-Challenge - Fase 1")
                                .version("v0.0.1")
                                .license(new License().name("restcontrol").url("https://github.com/RenatoCostaF/restcontrol"))
                );
    }
}