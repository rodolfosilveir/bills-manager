package br.com.totvs.bills_manager.adapter.in.rest.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SummaryAmountPaidResponse {
    
    private BigDecimal valorTotalPago;
}
