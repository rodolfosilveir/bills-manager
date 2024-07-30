package br.com.totvs.bills_manager.domain.exception;

import br.com.totvs.bills_manager.domain.model.Role;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(final String text) {
        super(String.format("Role '%s' não permitida. Roles permitidas: %s", text, Role.getDescriptions()));
    }
    
}
