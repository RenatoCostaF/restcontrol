package com.restocontrol.restcontrol_api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticationDTO(
        @Schema(description = "Login do usuário", example = "master.admin")
        @NotBlank(message = "Login is required")
        String login,

        @Schema(description = "Senha do usuário com no mínimo 6 caracteres", example = "senha123")
        @NotBlank(message = "Password is required")
        @Size(min = 6)
        String password) {
}
