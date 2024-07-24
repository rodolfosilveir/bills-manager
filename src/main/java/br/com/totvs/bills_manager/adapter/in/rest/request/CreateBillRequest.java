package br.com.totvs.bills_manager.adapter.in.rest.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateBillRequest {

    @NotNull(message = "dataVencimento é um campo obrigatorio")
    private LocalDate dataVencimento;

    @NotNull(message = "dataPagamento é um campo obrigatorio")
    private LocalDate dataPagamento;

    @NotNull(message = "valor é um campo obrigatorio")
    private BigDecimal valor;

    private String descricao;

    @NotNull(message = "situacao é um campo obrigatorio")
    private String situacao;

}
