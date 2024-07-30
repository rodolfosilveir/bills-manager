package br.com.totvs.bills_manager.adapter.in.rest.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Dados de criação da conta de pagamento")
public class CreateBillRequest {

    @NotNull(message = "dataVencimento é um campo obrigatorio")
    @Schema(description = "Data de vencimento da conta yyyy-MM-dd", example = "2024-12-31")
    private LocalDate dataVencimento;

    @Schema(description = "Data de pagamento da conta yyyy-MM-dd", example = "2024-08-14")
    private LocalDate dataPagamento;

    @NotNull(message = "valor é um campo obrigatorio")
    @Schema(description = "Valor da conta", example = "125.58")
    private BigDecimal valor;

    @Schema(description = "Descrição da conta de pagamento", example = "Qualquer descrição")
    private String descricao;

    @NotNull(message = "situacao é um campo obrigatorio")
    @Schema(description = "Situação da conta de pagamento", example = "a vencer")
    private String situacao;

}
