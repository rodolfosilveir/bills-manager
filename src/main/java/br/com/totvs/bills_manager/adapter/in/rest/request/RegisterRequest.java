package br.com.totvs.bills_manager.adapter.in.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterRequest(
    
    @Schema(description = "Usuário a ser cadastrado para acesso a aplicação", example = "simpleUser")
    String login, 
    
    @Schema(description = "Senha do usuário a ser cadastrado para acesso a aplicação", example = "senha123")
    String password, 
    
    @Schema(description = "Role de acesso do usuário a ser cadastrado", example = "user")
    String role) {
    
}
