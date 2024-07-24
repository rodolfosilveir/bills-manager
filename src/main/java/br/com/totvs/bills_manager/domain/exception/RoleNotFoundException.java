package br.com.totvs.bills_manager.domain.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(final String text) {
        super(String.format("Role '%s' não é encontrada", text));
    }
    
}
