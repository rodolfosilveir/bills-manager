package br.com.totvs.bills_manager.domain.exception;

public class InvalidSituationException extends RuntimeException {
    public InvalidSituationException(final String text) {
        super(String.format("Situação '%s' não é uma situação válida", text));
    }
}
