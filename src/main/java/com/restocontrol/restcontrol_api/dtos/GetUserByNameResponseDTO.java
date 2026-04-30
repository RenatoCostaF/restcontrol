package com.restocontrol.restcontrol_api.dtos;

import com.restocontrol.restcontrol_api.entities.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

public record GetUserByNameResponseDTO(
        @Schema(description = "Identificador do usuário", example = "8b0c5bb0-9614-4d0d-8cb8-2046714e1f5f")
        String id,
        @Schema(description = "Nome do usuário", example = "Master Admin")
        String name,
        @Schema(description = "E-mail do usuário", example = "master@example.com")
        String email,
        @Schema(description = "Endereço do usuário", example = "Rua das Flores, 123")
        String Address,
        @Schema(description = "Papel do usuário", allowableValues = {"dono_restaurante", "cliente"}, example = "cliente")
        UserRole role
) {
}
