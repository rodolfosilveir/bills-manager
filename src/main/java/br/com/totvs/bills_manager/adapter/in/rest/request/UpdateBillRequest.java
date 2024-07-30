package br.com.totvs.bills_manager.adapter.in.rest.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateBillRequest (
    
    @Schema(description = "Data de vencimento da conta yyyy-MM-dd", example = "2024-12-31")
    LocalDate dataVencimento,

    @Schema(description = "Data de pagamento da conta yyyy-MM-dd", example = "2024-08-14")
    LocalDate dataPagamento,

    @Schema(description = "Valor da conta", example = "125.58")
    BigDecimal valor,

    @Schema(description = "Descrição da conta de pagamento", example = "Qualquer descrição")
    String descricao

){}
