package com.restocontrol.restcontrol_api.controllers;

import com.restocontrol.restcontrol_api.dtos.*;
import com.restocontrol.restcontrol_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
@Tag(name = "Users", description = "Controller para CRUD de usuários")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Buscar usuários por nome",
            description = "Retorna os usuários encontrados pelo nome informado. Endpoint restrito a usuários com papel DONO_RESTAURANTE.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuários encontrados com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GetUserByNameResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token ausente, inválido ou expirado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado sem permissão para consultar usuários",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhum usuário encontrado para o nome informado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "users-not-found",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Users Not Found",
                                              "status": 404,
                                              "detail": "No users were found for requested name.",
                                              "instance": "/v1/user/joao",
                                              "name": "joao"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao buscar usuários",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping("/{name}")
    public ResponseEntity<List<GetUserByNameResponseDTO>> findByName(
            @Parameter(description = "Nome utilizado na busca", example = "Joao")
            @PathVariable("name") String name
    ) {
        logger.info("Recebida requisição para buscar usuários pelo nome: {}", name);
        var users = this.userService.findByName(name);
        logger.info("Busca por nome concluída com {} usuário(s) encontrado(s) para: {}", users.size(), name);
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Criar novo usuário",
            description = "Cria um novo usuário e valida unicidade de e-mail e login. Endpoint público.",
            security = {}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateUserResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Payload inválido, senha inválida ou papel de usuário inválido",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "E-mail ou login já cadastrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao criar usuário",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(
            @Valid @RequestBody CreateUserRequestDTO user
    ) {
        logger.info("Recebida requisição para criar usuário com login: {}", user.login());
        var response = this.userService.createUser(user);
        logger.info("Usuário criado com sucesso. Id: {}", response.id());
        return ResponseEntity.status(201).body(response);
    }


    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza nome, e-mail, login e endereço do usuário informado. Não altera senha.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token ausente, inválido ou expirado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado sem permissão para atualizar este cadastro",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "E-mail ou login já cadastrado para outro usuário",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao atualizar usuário",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @Parameter(description = "Identificador do usuário", example = "8b0c5bb0-9614-4d0d-8cb8-2046714e1f5f")
            @PathVariable("id") UUID id,
            @RequestBody UpdateUserRequestDTO userDto
    ) {
        logger.info("Recebida requisição para atualizar o usuário de id: {}", id);
        this.userService.updateUser(userDto, id);
        logger.info("Atualização do usuário concluída com sucesso. Id: {}", id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Remover usuário",
            description = "Remove o usuário pelo ID. Endpoint restrito a usuários com papel DONO_RESTAURANTE.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token ausente, inválido ou expirado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado sem permissão para remover usuários",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao remover usuário",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "Identificador do usuário", example = "8b0c5bb0-9614-4d0d-8cb8-2046714e1f5f")
            @PathVariable("id") UUID id
    ) {
        logger.info("Recebida requisição para remover o usuário de id: {}", id);
        this.userService.deleteUser(id);
        logger.info("Usuário removido com sucesso. Id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Alterar senha do usuário",
            description = "Altera a senha do usuário informado. Permitido para o próprio usuário autenticado ou para usuários com papel DONO_RESTAURANTE.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Payload inválido ou senha fora da regra mínima",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token ausente, inválido ou expirado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado sem permissão para alterar a senha deste cadastro",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao alterar senha",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PutMapping("/change-password/{id}")
    public ResponseEntity<Void> changePassword(
            @Parameter(description = "Identificador do usuário", example = "8b0c5bb0-9614-4d0d-8cb8-2046714e1f5f")
            @PathVariable UUID id,
            @Valid @RequestBody ChangePasswordDTO changePasswordDto
    ) {
        logger.info("Recebida requisição para alterar a senha do usuário de id: {}", id);
        this.userService.changePassword(changePasswordDto, id);
        logger.info("Senha do usuário alterada com sucesso. Id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
