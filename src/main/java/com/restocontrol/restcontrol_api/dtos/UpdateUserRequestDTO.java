package com.restocontrol.restcontrol_api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateUserRequestDTO(
        @Schema(description = "Novo nome do usuário", example = "Master Admin Filho")
        String name,
        @Schema(description = "Novo e-mail do usuário", example = "master.filho@example.com")
        String email,
        @Schema(description = "Novo login do usuário", example = "master.admin.filho")
        String login,
        @Schema(description = "Novo endereço do usuário", example = "Av. Central, 500")
        String address
) {
}
