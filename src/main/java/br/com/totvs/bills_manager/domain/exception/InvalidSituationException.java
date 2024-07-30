package br.com.totvs.bills_manager.domain.exception;

import br.com.totvs.bills_manager.domain.model.Situacao;

public class InvalidSituationException extends RuntimeException {
    public InvalidSituationException(final String text) {
        super(String.format("Situação '%s' não é uma situação válida. Valores válidos: %s ", text, Situacao.getDescriptions()));
    }
}
