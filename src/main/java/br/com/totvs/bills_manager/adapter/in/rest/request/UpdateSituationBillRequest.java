package br.com.totvs.bills_manager.adapter.in.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdateSituationBillRequest (
    
    @Schema(description = "Situação da conta de pagamento", example = "pago")
    @NotBlank String situacao) {
    
}
