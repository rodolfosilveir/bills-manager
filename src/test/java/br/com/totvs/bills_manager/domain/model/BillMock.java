package br.com.totvs.bills_manager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BillMock {

    public static Bill create(Integer id){
        return Bill.builder()
            .id(id)
            .dataVencimento(LocalDate.of(2024, 12, 31))
            .dataPagamento(LocalDate.of(2024, 12, 31))
            .valor(BigDecimal.valueOf(200L))
            .descricao("descrição")
            .situacao(Situacao.A_VENCER)
        .build();
    }

    public static Bill create(Integer id, String descricao, Situacao situacao){
        return Bill.builder()
            .id(id)
            .dataVencimento(LocalDate.of(2024, 12, 31))
            .dataPagamento(LocalDate.of(2024, 12, 31))
            .valor(BigDecimal.valueOf(200L))
            .descricao(descricao)
            .situacao(situacao)
        .build();
    }
    
}
