package br.com.totvs.bills_manager.domain.exception;

public class BillNotFoundException extends RuntimeException {
    public BillNotFoundException(final Integer id) {
        super("Conta com id " + id.toString() + " não encontrada");
    }
    
}
