package br.com.totvs.bills_manager.adapter.in.rest.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.totvs.bills_manager.domain.model.Bill;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder(access = AccessLevel.PRIVATE)
public class BillResponse {

    private Integer id;
    
    private LocalDate dataVencimento;

    private LocalDate dataPagamento;

    private BigDecimal valor;

    private String descricao;

    private String situacao;

    public static BillResponse createFromDomain(Bill domain){
        return BillResponse.builder()
            .id(domain.getId())
            .dataVencimento(domain.getDataVencimento())
            .dataPagamento(domain.getDataPagamento())
            .valor(domain.getValor())
            .descricao(domain.getDescricao())
            .situacao(domain.getSituacao().getText())
        .build();
    }
    
}
