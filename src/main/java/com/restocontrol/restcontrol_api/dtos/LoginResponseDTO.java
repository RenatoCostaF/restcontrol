package com.restocontrol.restcontrol_api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponseDTO(
        @Schema(description = "Token JWT para autenticação Bearer", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZW5hdG8uY29zdGEiLCJleHAiOjE3NTAwMDAwMDB9.assinatura")
        String token) {
}
