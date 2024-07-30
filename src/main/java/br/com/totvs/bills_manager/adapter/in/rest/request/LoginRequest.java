package br.com.totvs.bills_manager.adapter.in.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(
    
    @Schema(description = "Usuário cadastrado na aplicação", example = "admin")
    String login, 
    
    @Schema(description = "Senha do usuário cadastrado na aplicação", example = "admin123")
    String password) {
    
}
