package br.com.totvs.bills_manager.domain.model;

import br.com.totvs.bills_manager.domain.exception.RoleNotFoundException;
import lombok.Generated;
import lombok.Getter;

public enum Role {

    ADMIN("admin"),
    USER("user");

    @Getter
    private String text;

    Role(String role){
        this.text = role;
    }

    public static Role fromText(String text){
        for (Role role: Role.values()) {
            if (role.getText().toUpperCase().equals(text.toUpperCase())) {
                return role;
            }
        }

        throw new RoleNotFoundException(text);
    }

    @Generated
    public static String getDescriptions() {
        StringBuilder descriptions = new StringBuilder();
        for (Role role: Role.values()) {
            descriptions.append("'").append(role.getText()).append("', ");
        }
        return descriptions.substring(0, descriptions.length() - 2);
    }
    
}
