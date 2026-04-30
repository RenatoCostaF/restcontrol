package com.restocontrol.restcontrol_api.dtos;

import com.restocontrol.restcontrol_api.entities.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequestDTO(

        @Schema(description = "Nome do usuário", example = "Master Admin")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "E-mail do usuário", example = "master@example.com")
        @NotBlank(message = "Email is required")
        @Email
        String email,

        @Schema(description = "Login único do usuário", example = "master.admin")
        @NotBlank(message = "Login is required")
        String login,

        @Schema(description = "Senha com no mínimo 6 caracteres", example = "senha123")
        @NotBlank(message = "Password is required")
        @Size(min = 6)
        String password,

        @Schema(description = "Endereço do usuário", example = "Rua das Flores, 123")
        @NotBlank(message = "Address is required")
        String address,

        @Schema(description = "Papel do usuário", allowableValues = {"dono_restaurante", "cliente"}, example = "cliente")
        @NotNull(message = "Role is required")
        UserRole role) {
}
