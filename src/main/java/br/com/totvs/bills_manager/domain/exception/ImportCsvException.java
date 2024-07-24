package br.com.totvs.bills_manager.domain.exception;

public class ImportCsvException extends RuntimeException {
    public ImportCsvException(final String fileName, final String msg) {
        super(String.format("Erro ao importar o arquivo %s. %s", fileName, msg));
    }    
}
