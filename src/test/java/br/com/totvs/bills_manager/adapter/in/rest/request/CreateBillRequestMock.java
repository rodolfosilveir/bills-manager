package br.com.totvs.bills_manager.adapter.in.rest.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateBillRequestMock {
    
    public static CreateBillRequest create(){
        return CreateBillRequest.builder()
            .dataVencimento(LocalDate.of(2024, 12, 31))
            .dataPagamento(LocalDate.of(2024, 12, 31))
            .valor(BigDecimal.valueOf(200L))
            .descricao("descrição")
            .situacao("a vencer")
        .build();
    }
}
