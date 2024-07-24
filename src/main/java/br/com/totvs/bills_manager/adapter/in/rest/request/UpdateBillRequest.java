package br.com.totvs.bills_manager.adapter.in.rest.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateBillRequest (
    
    LocalDate dataVencimento,

    LocalDate dataPagamento,

    BigDecimal valor,

    String descricao

){}
