package br.com.totvs.bills_manager.port.out;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totvs.bills_manager.domain.model.Bill;

public interface BillPort {

    Bill save (Bill conta);

    Bill update (Bill conta);

    Optional<Bill> findById(Integer id);

    Page<Bill> findByDescricaoAndDataVencimento(String descricao, LocalDate dataVencimento, Pageable pageable);

    BigDecimal summaryAmountPaid(LocalDate dataPagamentoInicio, LocalDate dataPagamentoFim);

    List<Bill> saveAll(List<Bill> bills);
    
}
