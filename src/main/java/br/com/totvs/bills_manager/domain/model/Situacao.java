package br.com.totvs.bills_manager.domain.model;

import br.com.totvs.bills_manager.domain.exception.InvalidSituationException;
import lombok.Generated;
import lombok.Getter;

public enum Situacao {
    
    A_VENCER("a vencer"), 
    EM_ATRASO("em atraso"), 
    PAGO("pago");

    @Getter
    private String text;

    Situacao(String text) {
        this.text = text;
    }

    public static Situacao fromText(String text){
        for (Situacao status: Situacao.values()) {
            if (status.getText().toUpperCase().equals(text.toUpperCase())) {
                return status;
            }
        }

        throw new InvalidSituationException(text);
    }

    @Generated
    public static String getDescriptions() {
        StringBuilder descriptions = new StringBuilder();
        for (Situacao situacao: Situacao.values()) {
            descriptions.append("'").append(situacao.getText()).append("', ");
        }
        return descriptions.substring(0, descriptions.length() - 2);
    }
}
