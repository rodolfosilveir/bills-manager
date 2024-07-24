package br.com.totvs.bills_manager.adapter.in.rest.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateSituationBillRequest (@NotBlank String situacao) {
    
}
