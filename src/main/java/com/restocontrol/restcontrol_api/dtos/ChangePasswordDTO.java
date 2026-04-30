package com.restocontrol.restcontrol_api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDTO(
        @Schema(description = "Nova senha do usuário", example = "novaSenha123")
        @NotBlank(message = "Password is required")
        String password) {
}
