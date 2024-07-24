package br.com.totvs.bills_manager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateBillRequest;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateSituationBillRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter(value = AccessLevel.PRIVATE)
public class Bill {

    private Integer id;
    
    private LocalDate dataVencimento;

    private LocalDate dataPagamento;

    private BigDecimal valor;

    private String descricao;

    private Situacao situacao;

    public Bill update(UpdateBillRequest request) {
        if (Objects.nonNull(request)) {
            updateField(request.dataVencimento(), this::setDataVencimento);
            updateField(request.dataPagamento(), this::setDataPagamento);
            updateField(request.valor(), this::setValor);
            updateField(request.descricao(), this::setDescricao);
        }

        return this;
    }

    public Bill updateSituation(UpdateSituationBillRequest request) {
        if (Objects.nonNull(request)) {
            updateField(Situacao.fromText(request.situacao()), this::setSituacao);
        }

        return this;
    }
    
    private <T> void updateField(T value, Consumer<T> setter) {
        Optional.ofNullable(value).ifPresent(setter);
    }
}
