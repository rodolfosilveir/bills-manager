package br.com.totvs.bills_manager.port.in;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totvs.bills_manager.domain.model.Bill;

public interface FindBillUC {

    public Page<Bill> findByFilters(String descricao, LocalDate dataVencimento, Pageable pageable);

    public Bill findById(Integer id);

    public BigDecimal summaryAmountPaid(LocalDate dataPagamentoInicio, LocalDate dataPagamentoFim);
    
}
